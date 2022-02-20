package com.github.glo2003.payroll.domain;

import java.util.List;

public interface EmployeeRepo {
//todo remove employee list from CompanyPayroll
	public List<Employee> findAll();

	public Boolean isPresent(Employee employee);

	public List<Employee> findBy(RoleType role);

	public void save(Employee employee);

	public void update(Employee employee, Employee updatedEmployee);

	public void delete(Employee employee);
}
