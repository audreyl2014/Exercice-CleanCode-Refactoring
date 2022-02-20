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
private EmployeeRepo employeeRepo;
private ListEmployeeService listEmployeeService;
private List<Paycheck> paychecks;
private List<Boolean> holidays;

    public CompanyPayroll() {
        this.employees = new ArrayList<>();
        this.paychecks = new ArrayList<>();
        this.holidays = new ArrayList<>();
        employeeRepo = new EmployeeRepoInMemory();
        listEmployeeService = new ListEmployeeService(employeeRepo);
    }

    public void processPending() {
        employeeRepo.findAll().forEach(Employee::processPaycheck);
    }

    public void createPending() {
        employeeRepo.findAll().forEach(Employee::createPaycheck);
    }

    public List<Paycheck> getPending() {
        //createPending();
        List<Paycheck> pendingPaychecks = new ArrayList<>();
        this.employeeRepo.findAll().stream().forEach(employee -> {
            if(employee.paycheckIsPending()){
                pendingPaychecks.add(employee.getPaycheck());
            }
        });
        return pendingPaychecks;
    }

    public void takesPayedHoliday(Employee employee)
        throws NotEnoughDayException, EmployeeNotFoundException {
        //validateEmployee(employee);
        employee.takesPayedHoliday();
    }
    public void takeHoliday(Employee employee, Integer amount)
        throws NotEnoughDayException, EmployeeNotFoundException {
        validateEmployee(employee);
        employee.takesHoliday(amount);
        //employee.createPaycheck();
    }

    public void validateEmployee(Employee employee) throws EmployeeNotFoundException {
        if (!employeeRepo.isPresent(employee)) {
            throw new EmployeeNotFoundException();
        }
    }

    public void addEmployee(Employee employee) {
        employeeRepo.findAll().add(employee);
    }

    public void listEmployees() {
        listEmployeeService.listAllEmployee();
    }

    public List<Employee> findVicePresidents() {
        return employeeRepo.findBy(RoleType.VICE_PRESIDENT);
    }

    public List<Employee> findManagers() {
        return employeeRepo.findBy(RoleType.MANAGER);
    }

    public List<Employee> findEngineer() {
        return employeeRepo.findBy(RoleType.ENGINEER);
    }

    public List<Employee> findInterns() {
        return employeeRepo.findBy(RoleType.INTERN);
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
        he.setWagePerHour(he.getWagePerHour() + raise);
        } else if (e instanceof SalariedEmployee) {
            SalariedEmployee se = (SalariedEmployee) e;
            se.setBiweeklyIncomes(se.getBiweeklyIncomes() + raise);
        } else {
            throw new RuntimeException("something happened");
        }
    }

    ///Statistics
    public float avgPaycheckPending() {
        List<Paycheck> pendingPaychecks = getPending();
        float out_float = (float) pendingPaychecks.stream().mapToDouble(p -> p.getAmount()).sum();

        /*if (this.paychecks.size() == 0) {
            return -1f;
        }*/
        float t_float = getTotalmoney();
           out_float = t_float / this.employeeRepo.findAll().size();
        return out_float;
    }

    public int getNumEholidays() {
        int i_int = 0;
        for (int ii_int = 0; ii_int < holidays.size(); ++ii_int) {
            if (this.holidays.get(ii_int)) i_int++;
        }
        return i_int;
    }

    public float getTotalmoney() {
        List<Paycheck> paychecks = new ArrayList<>();
        this.employeeRepo.findAll().stream().forEach(employee -> {
            paychecks.add(employee.getPaycheck());
        });
        return (float) paychecks.stream().mapToDouble(p -> p.getAmount()).sum();
    }
}
