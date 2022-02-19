package com.github.glo2003.payroll.domain;

import java.util.Optional;

public class Holiday {
	private Boolean payout;
	private Integer amount;
	protected Boolean isTakingHoliday;

	public Holiday() {
		this.amount = null;
		this.payout = false;
		this.isTakingHoliday = false;
	}
	public  void set(Boolean payout, int amount) {
		validateHoliday(payout, amount);
		this.payout = payout;
		this.amount = (payout ? 5: amount);
	}

	void reset() {
		this.amount = null;
		this.payout = false;
		this.isTakingHoliday = false;
	}
	public int getAmount() { return getAmount(); }

	public Boolean isPayout() { return this.isPayout(); }

	public Boolean isTakingHoliday() {
		return this.isTakingHoliday;
	}

	private void validateHoliday(Boolean payout, Integer amountToValidate) {
		if (payout) {
			Optional.ofNullable(amountToValidate).orElseThrow( RuntimeException::new);//("amount must be null"); todo meilleur exception
		} else {
			Optional.of(amountToValidate).orElseThrow( RuntimeException::new);//("amount must be set")); todo meilleur exception
		}
	}
}
