package com.github.glo2003.payroll.Exceptions;

public class EmployeeNotFoundException extends Exception{
	public EmployeeNotFoundException(){
		super("This employee is not recorded or innexistent.");
	}
}
