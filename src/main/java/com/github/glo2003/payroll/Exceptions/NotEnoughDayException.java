package com.github.glo2003.payroll.Exceptions;

import com.github.glo2003.payroll.domain.Employee;

public class NotEnoughDayException extends Exception{
	public NotEnoughDayException(String name) {
		super("Employee " + name + " doesn't have the required number of vacation days left.");
	}
}
