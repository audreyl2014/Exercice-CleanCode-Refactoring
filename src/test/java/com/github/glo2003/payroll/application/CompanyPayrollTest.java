package com.github.glo2003.payroll.application;

import com.github.glo2003.payroll.CompanyPayroll;
import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.HourlyEmployee;
import com.github.glo2003.payroll.domain.Paycheck;
import com.github.glo2003.payroll.domain.SalariedEmployee;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

class CompanyPayrollTest {

    public static final float HOURLY_RATE = 10;
    public static final float HOURLY_AMOUNT = 25;
    public static final String HOURLY_NAME = "William";
    public static final String SALARIED_NAME = "Xavier";
    public static final float BIWEEKLY_AMOUNT = 10_000;
    public static final float RAISE = 10;
    public static final float ANOTHER_MONTHLY_AMOUNT = 20_000;
    public static final int VACATION_DAYS = 12;
    CompanyPayroll company;
    Employee vp;
    Employee eng;
    Employee manager;
    Employee intern1;
    Employee intern2;
    HourlyEmployee hourlyEmployee;
    SalariedEmployee salariedEmployee;
    SalariedEmployee anotherSalariedEmployee;

    @BeforeEach
    void setUp() {
        company = new CompanyPayroll();
        vp = new HourlyEmployee("Alice", "vp", 25, 100, 35.5f * 2);
        eng = new SalariedEmployee("Bob", "engineer", 4, 1500);
        manager = new SalariedEmployee("Charlie", "manager", 10, 2000);
        intern1 = new HourlyEmployee("Ernest", "intern", 10, 5, 50 * 2);
        intern2 = new HourlyEmployee("Fred", "intern", 10, 5, 50 * 2);

        hourlyEmployee = new HourlyEmployee(HOURLY_NAME, "engineer", VACATION_DAYS, HOURLY_RATE, HOURLY_AMOUNT);
        salariedEmployee = new SalariedEmployee(SALARIED_NAME, "engineer", VACATION_DAYS, BIWEEKLY_AMOUNT);
        anotherSalariedEmployee = new SalariedEmployee("Yan", "manager", VACATION_DAYS, ANOTHER_MONTHLY_AMOUNT);
    }

    @Test
    void createPendingsCreatesCorrectHourlyPaycheck() {
        company.addEmployee(hourlyEmployee);

        company.createPending();

        Paycheck paycheck = company.getPendings().get(0);
        assertThat(paycheck.getTo()).isEqualTo(HOURLY_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(HOURLY_RATE * HOURLY_AMOUNT);
    }

    @Test
    void createPendingsCreatesCorrectSalariedPaycheck() {
        company.addEmployee(salariedEmployee);

        company.createPending();

        Paycheck paycheck = company.getPendings().get(0);
        assertThat(paycheck.getTo()).isEqualTo(SALARIED_NAME);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_AMOUNT);
    }

    @Test
    void processPending_shouldRemovePendingPaychecks() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);
        company.createPending();

        company.processPending();

        assertThat(company.getPendings().size()).isEqualTo(0);
    }

    @Test
    void findSWE_shouldReturnSoftwareEngineers() {
        company.addEmployee(eng);

        List<Employee> es = company.findSoftwarEngineer();
        assertThat(es).containsExactly(eng);
    }

    @Test
    void findMgs_shouldReturnManagers() {
        company.addEmployee(manager);

        List<Employee> es = company.findManagers();
        assertThat(es).containsExactly(manager);
    }

    @Test
    void find_Vice_Presidents_shouldReturnVicePresidents() {
        company.addEmployee(vp);

        List<Employee> es = company.findVicePresidents();
        assertThat(es).containsExactly(vp);
    }

    @Test
    void find_interns_shouldReturnInterns() {
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        List<Employee> es = company.findInterns();
        assertThat(es).containsExactly(intern1, intern2);
    }

    @Test
    void createPending_shouldCreatePendingPaycheck() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.createPending();

        assertThat(company.getPendings().size()).isEqualTo(5);
    }

    @Test
    void hourlyEmployee() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.createPending();

        assertThat(company.getPendings().size()).isEqualTo(5);
    }

    @Test
    void hourlyRaiseShouldRaiseHourlySalary() {
        company.addEmployee(hourlyEmployee);

        company.salaryRaise(hourlyEmployee, RAISE);

        company.createPending();
        Paycheck paycheck = company.getPendings().get(0);
        assertThat(paycheck.getAmount()).isEqualTo((HOURLY_RATE + RAISE) * HOURLY_AMOUNT);
    }

    @Test
    void salariedRaiseShouldRaiseMonthlySalary() {
        company.addEmployee(salariedEmployee);

        company.salaryRaise(salariedEmployee, RAISE);

        company.createPending();
        Paycheck paycheck = company.getPendings().get(0);
        assertThat(paycheck.getAmount()).isEqualTo(BIWEEKLY_AMOUNT + RAISE);
    }

    @Test
    void negativeRaiseShouldThrow() {
        company.addEmployee(eng);

        Assert.assertThrows(RuntimeException.class, () -> company.salaryRaise(eng, -1));
    }

    @Test
    void cannotGiveRaiseIfNotInCompany() {
        Assert.assertThrows(RuntimeException.class, () -> company.salaryRaise(eng, 10));
    }

    @Test
    void salariedPayoutHolidays_paysOneWeek() {
        company.addEmployee(salariedEmployee);

        company.takeHoliday(salariedEmployee, true, null);

        Paycheck pending = company.getPendings().get(0);
        assertThat(pending.getAmount()).isEqualTo(BIWEEKLY_AMOUNT / 2);
        assertThat(salariedEmployee.getVacationDays()).isEqualTo(VACATION_DAYS - 5);
    }


    @Test
    void salariedHolidays_removesVacantionDays() {
        company.addEmployee(salariedEmployee);
        int amount = 2;

        company.takeHoliday(salariedEmployee, false, amount);

        assertThat(company.getPendings()).hasSize(0);
        assertThat(salariedEmployee.getVacationDays()).isEqualTo(VACATION_DAYS - amount);
    }

    @Test
    void hourlyPayoutHolidays_paysOneWeek() {
        company.addEmployee(hourlyEmployee);

        company.takeHoliday(hourlyEmployee, true, null);

        Paycheck pending = company.getPendings().get(0);
        assertThat(pending.getAmount()).isEqualTo(HOURLY_AMOUNT * HOURLY_RATE / 2f);
        assertThat(hourlyEmployee.getVacationDays()).isEqualTo(VACATION_DAYS - 5);
    }


    @Test
    void hourlyHolidays_removesVacantionDays() {
        company.addEmployee(hourlyEmployee);
        int amount = 2;

        company.takeHoliday(hourlyEmployee, false, amount);

        assertThat(company.getPendings()).hasSize(0);
        assertThat(hourlyEmployee.getVacationDays()).isEqualTo(VACATION_DAYS - amount);
    }

    @Test
    void avgPayCehck_pending() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.createPending();

        float avg = company.avgPayCehck_pending();

        assertThat(avg).isEqualTo((BIWEEKLY_AMOUNT + ANOTHER_MONTHLY_AMOUNT) / 2);
    }

    @Test
    void getTotalmoney() {
        company.addEmployee(salariedEmployee);
        company.addEmployee(anotherSalariedEmployee);
        company.createPending();

        float t = company.getTotalmoney();

        assertThat(t).isEqualTo(BIWEEKLY_AMOUNT + ANOTHER_MONTHLY_AMOUNT);
    }

    @Test
    void getNumEholidaysShouldReturnZeroWhenEveryoneWorks() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        int x = company.getNumEholidays();

        assertThat(x).isEqualTo(0);
    }

    @Test
    void getNumEholidaysShouldReturnNumberOfPeopleInHolidays() {
        company.addEmployee(vp);
        company.addEmployee(eng);
        company.addEmployee(manager);
        company.addEmployee(intern1);
        company.addEmployee(intern2);

        company.takeHoliday(vp, false, 2);
        company.takeHoliday(vp, false, 2);
        company.takeHoliday(eng, false, 2);
        company.takeHoliday(manager, false, 2);

        int x = company.getNumEholidays();
        assertThat(x).isEqualTo(3);
    }
}