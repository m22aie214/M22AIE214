package com.prateek_M22AIE214.springboot.thymeleafdemo.dao;

import com.prateek_M22AIE214.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// that's it ... no need to write any code LOL!

    //add a method sort by last name

    public List<Employee> findAllByOrderByLastNameAsc();
}
