package com.github.glo2003.payroll.domain;


public abstract class Employee {
    private String name;
    private String role;
    private int vacationDays;
    protected Paycheck paycheck;
    protected Holiday holiday;

    public Employee(String name, String role, int vacationDays) {
        this.name = name;
        this.role = role;
        this.vacationDays = vacationDays;
        this.paycheck = new Paycheck();
        this.holiday = new Holiday();
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getVacationDays() {
        return vacationDays;
    }

    public void setVacationDays(int vacationDays) {
        this.vacationDays = vacationDays;
    }

    public abstract void createPaycheck();

    public void takesHoliday(Boolean payout, int amount){
        if(!payout && this.vacationDays < amount){
            throw new RuntimeException("not enough vacation day");
        }
        this.holiday.set(payout, amount);
    }

    public void processPaycheck() {
        if(this.paycheck.isPending){
            System.out.println("Sending" + paycheck.getAmount() + "$ to " + this.getName());
            this.paycheck.resetIsPending();
        }
    }


    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", vacation_days=" + vacationDays +
                '}';
    }
}
