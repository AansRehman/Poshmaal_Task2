package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> findAllEmployees();

    Employee findEmployee(String email);

    int addEmployee(Employee employee);

    Employee updateEmployee(String email, Employee employee);

    int deleteEmployee(String email);

    int updateEmployeePassword(String email, String newPassword);


}
