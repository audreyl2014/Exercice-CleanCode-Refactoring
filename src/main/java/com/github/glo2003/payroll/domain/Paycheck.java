package com.github.glo2003.payroll.domain;

public class Paycheck {
    private final String toEmployee;
    private float amount;
    private Boolean isPending;

    public Paycheck(String name) {
        this.toEmployee = name;
        this.amount = 0;
        this.isPending = false;
    }

    public Boolean isPending() {
        return isPending;
    }

    public String getToEmployee() {
        return toEmployee;
    }

    public float getAmount() {
        return amount;
    }

    public  void setIsPending(Boolean isPending){
        this.isPending = isPending;
    }

    public void process() {
        if(this.isPending){
            System.out.println("Sending " + this.amount + "$ to " + toEmployee);
        }
        resetIsPending();
    }

    public void createPending(Float amount) {
        this.amount = amount;
        this.isPending = true;
    }

    public void resetIsPending() {
        setIsPending(false);
        this.amount = 0;
    }
}
