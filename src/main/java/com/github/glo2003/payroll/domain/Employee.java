package com.github.glo2003.payroll.domain;

import com.github.glo2003.payroll.Exceptions.NotEnoughDayException;

public abstract class Employee {
    protected static final int DAYS_PAYEDOUT = 5;
    private String name;
    private RoleType role;
    private int vacationDays;
    protected Paycheck paycheck;
    protected Holiday holiday;

    public Employee(String name, RoleType role, int vacationDays) {
        this.name = name;
        this.role = role;
        this.vacationDays = vacationDays;
        this.paycheck = new Paycheck(this.name);
        this.holiday = new Holiday();
    }

    public abstract void createPaycheck();

    public String getName() {
        return name;
    }

    public RoleType getRole() {
        return role;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public Paycheck getPaycheck() {
        return this.paycheck;
    }

    public Boolean paycheckIsPending() {
        return this.paycheck.isPending();
    }

    public void processPaycheck() { this.paycheck.process(); }

    public void takesPayedHoliday() throws NotEnoughDayException {
        vacationDaysValidation(DAYS_PAYEDOUT);
        this.holiday.setPayedOutHoliday();
        this.holiday.set(DAYS_PAYEDOUT);
        this.createPaycheck();
    }

    public void takesHoliday(Integer numberOfDays) throws NotEnoughDayException {
        vacationDaysValidation(numberOfDays);
        this.holiday.set(numberOfDays);
        this.createPaycheck();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", vacation_days=" + vacationDays +
                '}';
    }

    private void vacationDaysValidation(Integer numberOfDays) throws NotEnoughDayException {
        if(this.vacationDays < numberOfDays){
            throw new NotEnoughDayException(this.name);
        }
    }
}
