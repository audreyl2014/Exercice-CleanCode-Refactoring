package com.github.glo2003.payroll.service;

import com.github.glo2003.payroll.domain.Employee;
import com.github.glo2003.payroll.domain.EmployeeRepo;
import java.util.List;

public class ListEmployeeService {
	final private EmployeeRepo employeeRepo;

	public ListEmployeeService(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

	public void all(){
		listVicePresidents();
		listManagers();
		listEngineers();
		listInters();
	}

	private void listVicePresidents(){
		System.out.println("Vice presidents:");
		present(this.employeeRepo.findBy("vp"));
	}

	private void listManagers(){
		System.out.println("Managers:");
		present(this.employeeRepo.findBy("manager"));
	}

	private void listEngineers(){
		System.out.println("Engineers:");
		present(this.employeeRepo.findBy("engineer"));
	}
	private void listInters(){
		System.out.println("Interns:");
		present(this.employeeRepo.findBy("intern"));
	}
	private void present(List<Employee> employees){
		employees.forEach(e -> System.out.println("\t" + e.toString()));
	}
}
