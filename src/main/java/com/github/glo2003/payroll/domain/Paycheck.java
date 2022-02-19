package com.github.glo2003.payroll.domain;

public class Paycheck {
    private String to; //todo enlever?
    private float amount;
    protected Boolean isPending;

    public Paycheck() {
        this.to = to;
        this.amount = amount;
        this.isPending = false;
    }

    public String getTo() {
        return to;
    }

    public float getAmount() {
        return amount;
    }

    public  void setIsPending(Boolean isPending){
        this.isPending = isPending;
    }

    public void resetIsPending() {
        setIsPending(false);
        this.amount = 0;
        }

    public void createPending(Float amount) {
        this.amount = amount;
        this.isPending = true;
    }
}
