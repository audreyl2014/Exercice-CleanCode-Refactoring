package com.github.glo2003.payroll.domain;

public enum RoleType {
	VICEPRESIDENT("vp"),
	ENGINEER("engineer"),
	MANAGER("manager"),
	INTERN("intern");

	private final String type;

	RoleType(String type) {
		this.type = type;
	}
}
