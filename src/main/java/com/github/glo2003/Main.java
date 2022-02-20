package com.github.glo2003;

import com.github.glo2003.payroll.Exceptions.NotEnoughDayException;
import com.github.glo2003.payroll.application.CompanyPayroll;
import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.HourlyEmployee;
import com.github.glo2003.payroll.domain.RoleType;
import com.github.glo2003.payroll.domain.SalariedEmployee;

public class Main {

    public static void main(String[] args) throws Exception {
        CompanyPayroll companyPayroll = new CompanyPayroll();

        Employee e1 = new HourlyEmployee("Alice", RoleType.VICE_PRESIDENT, 25, 100, 35.5f * 4);
        Employee e2 = new SalariedEmployee("Bob", RoleType.ENGINEER, 4, 1500);
        Employee e3 = new SalariedEmployee("Charlie", RoleType.MANAGER, 4, 2000);
        Employee e4 = new HourlyEmployee("Ernest", RoleType.INTERN, 1, 5, 50 * 4);
        Employee e5 = new HourlyEmployee("Fred", RoleType.INTERN, 1, 5, 50 * 4);

        companyPayroll.addEmployee(e1);
        companyPayroll.addEmployee(e2);
        companyPayroll.addEmployee(e3);
        companyPayroll.addEmployee(e4);
        companyPayroll.addEmployee(e5);

        System.out.println("----- Listing employees -----");
        companyPayroll.listEmployees();

        System.out.println("----- Giving raises -----");
        companyPayroll.salaryRaise(e1, 10);
        companyPayroll.salaryRaise(e2, 100);

        System.out.println("\n----- Holidays -----");
        companyPayroll.takesPayedHoliday(e1);
        companyPayroll.takeHoliday(e2,10);
        companyPayroll.takesPayedHoliday(e3);
        System.out.println("Number of employees in holidays: " + companyPayroll.getNumEholidays());

        System.out.println("\n----- Create paychecks -----");
        companyPayroll.createPending();

        System.out.println("\n----- Pay statistics -----");
        float t = companyPayroll.getTotalmoney();
        System.out.println("Total money spent: ");
        float avg = companyPayroll.avgPaycheckPending();
        System.out.println("Average paycheck: " + avg);

        System.out.println("\n----- Pay -----");
        companyPayroll.processPending();
    }
}
