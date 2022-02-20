package com.github.glo2003.payroll.domain;

public class Holiday {
	private Boolean payout;
	private Integer numberOfDays;
	protected Boolean isTakingHoliday;

	public Holiday() {
		this.numberOfDays = 0;
		this.payout = false;
		this.isTakingHoliday = false;
	}
	
	public void setPayedOutHoliday(){
		this.payout = true;
	}

	public  void set(Integer amount) {
		this.numberOfDays = amount;
		this.isTakingHoliday=true;
	}

	void reset() {
		this.numberOfDays = null;
		this.payout = false;
		this.isTakingHoliday = false;
	}
	public int getNumberOfDays() { return this.numberOfDays; }

	public Boolean isPayout() { return this.payout; }

	public Boolean isTakingHoliday() {
		return this.isTakingHoliday;
	}
}
