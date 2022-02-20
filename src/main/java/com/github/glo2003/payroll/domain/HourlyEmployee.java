package com.github.glo2003.payroll.domain;

public class HourlyEmployee extends Employee {
    private float rate;
    private float amount;

    public HourlyEmployee(String name, String role, int vacationDays, float rate, float amount) {
        super(name, role, vacationDays);
        this.rate = rate;
        this.amount = amount;
    }

    public float getRate() {
        return rate;
    }

    public float getAmount() {
        return amount;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @Override
    public void createPaycheck() {
        //todo add error (dans super?)
        /*if (!payout && e.getVacationDays() < amount) { // cannot
            throw new RuntimeException("error");
        }*/
       // if(this.pacheckIsPending()) { return;}
        if(!this.holiday.isTakingHoliday) {
            this.paycheck.createPending(this.amount * this.rate);
        }else if(this.holiday.isPayout()) {
            this.paycheck.createPending(this.holiday.getAmount() * this.rate / 2f);
            this.setVacationDays(this.getVacationDays() - this.holiday.getAmount());
        }else {
            this.setVacationDays(this.getVacationDays() - this.holiday.getAmount());
        }
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacation_days=" + this.getVacationDays() +
                ", hourlyRate=" + rate +
                ", amount=" + amount +
                '}';
    }
}
