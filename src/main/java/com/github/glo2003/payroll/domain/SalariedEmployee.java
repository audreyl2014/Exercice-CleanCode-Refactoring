package com.github.glo2003.payroll.domain;

public class SalariedEmployee extends Employee {
    private float biweeklyIncomes;

    public SalariedEmployee(String name, RoleType role, int vacationDays, float biweeklyIncomes) {
        super(name, role, vacationDays);
        this.biweeklyIncomes = biweeklyIncomes;
    }

    public float getBiweeklyIncomes() {
        return biweeklyIncomes;
    }

    public void setBiweeklyIncomes(float biweeklyIncomes) {
        this.biweeklyIncomes = biweeklyIncomes;
    }

    @Override
    public void createPaycheck() {
        if(!this.holiday.isTakingHoliday) {
            this.paycheck.createPending(this.biweeklyIncomes);
        }else if(this.holiday.isPayout()) {
            this.paycheck.createPending(this.biweeklyIncomes / 2f);
            this.setVacationDays(this.getVacationDays() - this.holiday.getNumberOfDays());
        }else {
            this.setVacationDays(this.getVacationDays() - this.holiday.getNumberOfDays());
        }
    }

    @Override
    public String toString() {
        return "SalariedEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacation_days=" + this.getVacationDays() +
                ", monthly=" + biweeklyIncomes +
                '}';
    }
}
