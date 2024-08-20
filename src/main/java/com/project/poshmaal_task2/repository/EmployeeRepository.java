package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Employee;

public interface EmployeeRepository {
    Employee changeEmplyeePassword(long id, String password);
}
