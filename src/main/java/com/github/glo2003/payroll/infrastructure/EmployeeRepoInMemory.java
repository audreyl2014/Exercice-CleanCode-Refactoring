package com.github.glo2003.payroll.infrastructure;

import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.EmployeeRepo;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepoInMemory implements EmployeeRepo {
	private List<Employee> employees;

	public EmployeeRepoInMemory() {
		this.employees= new ArrayList<>();
	}

	@Override
	public List<Employee> findAll() {
		return this.employees;
	}

	@Override
	public Boolean isPresent(Employee employee) {
		return this.employees.contains(employee);
	}

	@Override
	public List<Employee> findBy(String role) {
		List<Employee> employeesByRole = new ArrayList<>();
		this.employees.forEach(employee -> {
			if(employee.getRole().equals(role)){
				employeesByRole.add(employee);
			}
		});
		return employeesByRole;
	}

}
