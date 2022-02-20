package com.github.glo2003.payroll.application;

import com.github.glo2003.payroll.Exceptions.EmployeeNotFoundException;
import com.github.glo2003.payroll.Exceptions.NotEnoughDayException;
import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.EmployeeRepo;
import com.github.glo2003.payroll.domain.HourlyEmployee;
import com.github.glo2003.payroll.domain.Paycheck;
import com.github.glo2003.payroll.domain.RoleType;
import com.github.glo2003.payroll.domain.SalariedEmployee;
import com.github.glo2003.payroll.infrastructure.EmployeeRepoInMemory;
import com.github.glo2003.payroll.service.ListEmployeeService;
import java.util.ArrayList;
import java.util.List;

public class CompanyPayroll {
final private List<Employee> employees;
private final EmployeeRepo employeeRepo;
private final ListEmployeeService listEmployeeService;

    public CompanyPayroll() {
        this.employees = new ArrayList<>();
        employeeRepo = new EmployeeRepoInMemory();
        listEmployeeService = new ListEmployeeService(employeeRepo);
    }

    public void addEmployee(Employee employee) {
        employeeRepo.findAll().add(employee);
    }

    public void listEmployees() {
        listEmployeeService.listAllEmployee();
    }

    public List<Employee> findVicePresidents() { return employeeRepo.findBy(RoleType.VICE_PRESIDENT); }

    public List<Employee> findManagers() {
        return employeeRepo.findBy(RoleType.MANAGER);
    }

    public List<Employee> findEngineer() {
        return employeeRepo.findBy(RoleType.ENGINEER);
    }

    public List<Employee> findInterns() {
        return employeeRepo.findBy(RoleType.INTERN);
    }

    public void processPending() { employeeRepo.findAll().forEach(Employee::processPaycheck); }

    public void createPending() { employeeRepo.findAll().forEach(Employee::createPaycheck); }

    public List<Paycheck> getPending() {
        List<Paycheck> pendingPaychecks = new ArrayList<>();
        this.employeeRepo.findAll().forEach(employee -> {
            if(employee.paycheckIsPending()){
                pendingPaychecks.add(employee.getPaycheck());
            }
        });
        return pendingPaychecks;
    }

    public float avgPaycheckPending() {
        float out_float;
        float t_float = getTotalMoney();
        out_float = t_float / this.employeeRepo.findAll().size();
        return out_float;
    }

    public void takesPayedHoliday(Employee employee)
        throws NotEnoughDayException, EmployeeNotFoundException {
        validateEmployee(employee);
        employee.takesPayedHoliday();
    }
    public void takeHoliday(Employee employee, Integer amount)
        throws NotEnoughDayException, EmployeeNotFoundException {
        validateEmployee(employee);
        employee.takesHoliday(amount);
    }

    public void validateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (!employeeRepo.isPresent(employee)) {
            throw new EmployeeNotFoundException();
        }
    }

    public int getTotalHolidays() { //todo change pour count() on veut le nombre de personne
        return employeeRepo.findAll().stream().mapToInt(Employee::getHolidays).sum();
    }

    public float getTotalMoney() {
        List<Paycheck> paychecks = new ArrayList<>();
        this.employeeRepo.findAll().forEach(employee -> {
            paychecks.add(employee.getPaycheck());
        });
        return (float) paychecks.stream().mapToDouble(Paycheck::getAmount).sum();
    }

    public void salaryRaise(Employee e, float raise) { //todo a compléter
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
            he.setWagePerHour(he.getWagePerHour() + raise);
        } else if (e instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) e;
            se.setBiweeklyIncomes(se.getBiweeklyIncomes() + raise);
        } else {
            throw new RuntimeException("something happened");
        }
    }
}
