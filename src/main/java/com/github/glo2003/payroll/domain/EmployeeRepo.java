package com.github.glo2003.payroll.domain;

import java.util.List;

public interface EmployeeRepo {
	List<Employee> findAll();

	Boolean isPresent(Employee employee);

	List<Employee> findBy(RoleType role);

	void save(Employee employee);

	void update(Employee employee, Employee updatedEmployee);

	void delete(Employee employee);
}
