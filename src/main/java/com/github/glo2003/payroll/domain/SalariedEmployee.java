package com.github.glo2003.payroll.domain;

public class SalariedEmployee extends Employee {
    private float biweekly;

    public SalariedEmployee(String name, String role, int vacationDays, float biweekly) {
        super(name, role, vacationDays);
        this.biweekly = biweekly;
    }

    public float getBiweekly() {
        return biweekly;
    }

    public void setBiweekly(float biweekly) {
        this.biweekly = biweekly;
    }

    @Override
    public void createPaycheck() {
        //todo add error (dans super?)
        /*if (!payout && e.getVacationDays() < amount) { // cannot
            throw new RuntimeException("error");
        }*/
        if(!this.holiday.isTakingHoliday) {
            this.paycheck.createPending(this.biweekly);
        }else if(this.holiday.isPayout()) {
            this.paycheck.createPending(this.biweekly / 2f);
            this.setVacationDays(this.getVacationDays() - this.holiday.getAmount());
        }else {
            this.setVacationDays(this.getVacationDays() - this.holiday.getAmount());
        }
    }

   /* @Override
    public void takesHoliday(Boolean payout, Integer amount) {

    }*/

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacation_days=" + this.getVacationDays() +
                ", monthly=" + biweekly +
                '}';
    }
}
