package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    EmployeeRepository employeeRepository;
}
