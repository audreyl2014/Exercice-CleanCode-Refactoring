package com.github.glo2003.payroll.Exceptions;

public class NotEmployedException extends Exception{
	public NotEmployedException() {
		super("This person is not working for the company.");
	}
}
