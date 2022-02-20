package com.github.glo2003.payroll.domain;

public class HourlyEmployee extends Employee {
    private float wagePerHour;
    private final float hoursForTwoWorkedWeeks;

    public HourlyEmployee(String name, RoleType role, int vacationDays, float wagePerHour, float hoursForTwoWorkedWeeks) {
        super(name, role, vacationDays);
        this.wagePerHour = wagePerHour;
        this.hoursForTwoWorkedWeeks = hoursForTwoWorkedWeeks;
    }

    public float getWagePerHour() {
        return wagePerHour;
    }

    public void setWagePerHour(float wagePerHour) {
        this.wagePerHour = wagePerHour;
    }

    @Override
    public void createPaycheck() {
        if(!this.holiday.isTakingHoliday) {
            this.paycheck.createPending(this.hoursForTwoWorkedWeeks * this.wagePerHour);
        }else if(this.holiday.isPayout()) {
            this.paycheck.createPending(this.hoursForTwoWorkedWeeks * this.wagePerHour / 2f);
            this.setVacationDays(this.getVacationDays() - this.holiday.getNumberOfDays());
        }else {
            this.setVacationDays(this.getVacationDays() - this.holiday.getNumberOfDays());
        }
    }

    @Override
    public String toString() {
        return "HourlyEmployee{" +
                "name='" + this.getName() + '\'' +
                ", role='" + this.getRole() + '\'' +
                ", vacation_days=" + this.getVacationDays() +
                ", hourlyRate=" + wagePerHour +
                ", amount=" + hoursForTwoWorkedWeeks +
                '}';
    }
}
