package com.project.poshmaal_task2.controller;

import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.model.Employee;
import com.project.poshmaal_task2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;

    @GetMapping("/findAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAllEmployees();
        if(employees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(employees, HttpStatus.OK);
        }
    }

    @GetMapping("/getEmployeeByEmail/{email}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("email") String email){
        Employee employee = employeeRepository.findEmployee(email);
        if (employee!=null){
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee){
        int res = employeeRepository.addEmployee(employee);
        if (res==1){
            return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Failed to add employee", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/updateEmployee/{email}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("email") String email, @RequestBody Employee employee){
        Employee employee1 = employeeRepository.updateEmployee(email, employee);
        if(employee1!=null){
            return new ResponseEntity<>(employee1, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteEmployee/{email}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("email") String email){
        int res = employeeRepository.deleteEmployee(email);
        if (res==1){
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Failed to delete employee", HttpStatus.NOT_FOUND);
        }
    }

    @QueryMapping(name = "findAllEmployees")
    public List<Employee> findAllEmployees(){

        List<Employee> employees = employeeRepository.findAllEmployees();
        if(!employees.isEmpty()){
            return employees;
        }else {
            return null;
        }
    }

    @QueryMapping(name = "getEmployee")
    public Employee findEmployeeByEmail(@Argument String email){

        Employee employee = employeeRepository.findEmployee(email);
        if(employee!=null){
            return employee;
        }else {
            return null;
        }
    }

    @MutationMapping(name = "deleteEmployee")
    public Boolean deleteEmployeeByEmail(@Argument String email){
        Employee employee = employeeRepository.findEmployee(email);
        if(employee!=null){
            int res = employeeRepository.deleteEmployee(email);
            if(res==1) {
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }


}
