package com.prateek_M22AIE214.springboot.thymeleafdemo.service;

import java.util.List;

import com.prateek_M22AIE214.springboot.thymeleafdemo.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	
	Employee findById(int theId);
	
	void save(Employee theEmployee);
	
	void deleteById(int theId);
	
}
