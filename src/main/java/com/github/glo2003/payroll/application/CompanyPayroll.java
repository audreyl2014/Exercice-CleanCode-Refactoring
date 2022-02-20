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
import java.util.stream.Collectors;

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
        employeeRepo.findAll().forEach(Employee::processPaycheck);
    }

    public void createPending() {
        employeeRepo.findAll().forEach(Employee::createPaycheck);
    }

    public List<Paycheck> getPendings() {
        List<Paycheck> paychecks = new ArrayList<>();
        this.employeeRepo.findAll().stream().forEach(employee -> {
            if(employee.pacheckIsPending()){
                paychecks.add(employee.getPaycheck());
            }
        });
        return paychecks;
    }

    public void takeHoliday(Employee employee, boolean payout, Integer amount) {
        if (!employeeRepo.isPresent(employee)) {
            throw new RuntimeException("not here");
        }
        employee.takesHoliday(payout, amount);
        employee.createPaycheck();
    }

    public void addEmployee(Employee employee) {
        employeeRepo.findAll().add(employee);
    }

    public void listEmployees() {
        listEmployeeService.all();
    }

    public List<Employee> findVicePresidents() {
        return employeeRepo.findBy("vp");
    }

    public List<Employee> findManagers() {
        return employeeRepo.findBy("manager");
    }

    public List<Employee> findSoftwareEngineer() {
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

    ///Statistics
    public float avgPaycheckPending() {
        List<Paycheck> pendingPaychecks = getPendings();
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
