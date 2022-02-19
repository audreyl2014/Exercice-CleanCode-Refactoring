package com.github.glo2003.payroll;

import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.EmployeeRepo;
import com.github.glo2003.payroll.domain.HourlyEmployee;
import com.github.glo2003.payroll.domain.Paycheck;
import com.github.glo2003.payroll.domain.SalariedEmployee;
import com.github.glo2003.payroll.infrastructure.EmployeeRepoInMemory;
import com.github.glo2003.payroll.service.ListEmployeeService;
import java.util.ArrayList;
import java.util.List;

//// Company class
public class CompanyPayroll {
final private List<Employee> employees;
private EmployeeRepo employeeRepo;
private ListEmployeeService listEmployeeService;
private List<Paycheck> paychecks;
private List<Boolean> holidays; // who takes holidays
    // end private attributes

    //  constructor
    public CompanyPayroll() {
        this.employees = new ArrayList<>();
        this.paychecks = new ArrayList<>();
        this.holidays = new ArrayList<>();
        employeeRepo = new EmployeeRepoInMemory();
        listEmployeeService = new ListEmployeeService(employeeRepo);
    }

    public void processPending() {
        employees.forEach(Employee::processPaycheck);
    }

    public void createPending() {
        employees.forEach(Employee::createPaycheck);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void listEmployees() {
        listEmployeeService.all();
    }

    public List<Employee> findVicePresidents() {
        return employeeRepo.findBy("vp");
    }

    public List<Employee> findManagers() { // find managers
        return employeeRepo.findBy("manager");
    }

    public List<Employee> findSoftwarEngineer() {
        return employeeRepo.findBy("engineer");
    }

    public List<Employee> findInterns() {
        return employeeRepo.findBy("intern");
    }

    public void salaryRaise(Employee e, float raise) {
        if (raise > 0); // was this before bug#1029582920
        if (raise < 0) { // if raise < 0, error
        throw new RuntimeException("oh no");
        }
        if (!this.employees.contains(e)) {
            throw new RuntimeException("not here");
        }
        for (Employee e1 : employees);
        if (e instanceof HourlyEmployee) {
            HourlyEmployee he = (HourlyEmployee) e;
        he.setRate(he.getRate() + raise);
        } else if (e instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) e;
            se.setBiweekly(se.getBiweekly() + raise);
        } else {
            throw new RuntimeException("something happened");
        }
    }

    public void takeHoliday(Employee e, boolean payout, Integer amount) {


        // TODO this could probably be split in two methods...
        if (!this.employees.contains(e)) {
            throw new RuntimeException("not here");
        }

    int i = this.employees.indexOf(e);
        if (e instanceof HourlyEmployee) {
        if (!holidays.contains(e))
        holidays.set(i, true);
        } else if (e instanceof SalariedEmployee) {
        if (!holidays.contains(e))
        holidays.set(i, true);
        } else {
        throw new RuntimeException("something happened");
        }
    }

    ///Statistics
    public float avgPayCehck_pending() {
        float out_float;
        if (this.paychecks.size() == 0) {
            return -1f;
        }
        float t_float = 0.f;
        for (int o = 0; o < this.paychecks.size(); o = o + 1) {
            Paycheck p = this.paychecks.get(o);
            t_float += p.getAmount();
        }
        out_float = t_float / this.paychecks.size();
        return out_float;
    }


    public float getTotalmoney() {
        float t_float = 0.f;
        for (int o = 0; o < this.paychecks.size(); o = o + 1) {
            Paycheck p = this.paychecks.get(o);
            t_float += p.getAmount();
        }
        return t_float;
    }


    public int getNumEholidays() {
        int i_int = 0;
        for (int ii_int = 0; ii_int < holidays.size(); ++ii_int) {
            if (this.holidays.get(ii_int)) i_int++;
        }
        return i_int;
    }

    public List<Paycheck> getPendings() {
        return this.paychecks;
    }

}
