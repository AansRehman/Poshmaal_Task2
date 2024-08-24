package com.project.poshmaal_task2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.poshmaal_task2.model.Artist;
import com.project.poshmaal_task2.model.Employee;
import com.project.poshmaal_task2.repository.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public ResponseEntity<String> addEmployee(@Valid @RequestBody Employee employee){

        System.out.println(employee.getPassword());
        if (!isValidPassword(employee.getPassword())) {
            System.out.println(!isValidPassword(employee.getPassword()));
            return new ResponseEntity<>("Password must be at least 8 characters long and contain uppercase, lowercase, and digits.", HttpStatus.BAD_REQUEST);
        }

        int res = employeeRepository.addEmployee(employee);
        if (res==1){
            return new ResponseEntity<>("Employee added successfully", HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>("Failed to add employee", HttpStatus.NOT_MODIFIED);
        }
    }

    @PutMapping("/updateEmployee/{email}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("email") String email, @Valid @RequestBody Employee employee){
        System.out.println(employee.getPassword());
        if (!isValidPassword(employee.getPassword())) {
            System.out.println(!isValidPassword(employee.getPassword()));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

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

//    @PatchMapping("/{email}/password")
//    public ResponseEntity<String> updateEmployeePassword(@PathVariable("email") String email, @RequestParam("newPassword") String newPassword) {
//        if (!isValidPassword(newPassword)) {
//            System.out.println(isValidPassword(newPassword));
//            return new ResponseEntity<>("Password must be at least 8 characters long and contain uppercase, lowercase, and digits.", HttpStatus.BAD_REQUEST);
//        }
//
//        int result = employeeRepository.updateEmployeePassword(email, newPassword);
//        if (result == 1) {
//            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to update password", HttpStatus.NOT_FOUND);
//        }
//    }

    private boolean isValidPassword(String password) throws IllegalArgumentException
    {
//        System.out.println("Password: "+password);
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        if (!password.matches(regex))
            throw new IllegalArgumentException("Password must be 8-20 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");

        return password.matches(regex);
    }


    @PatchMapping("/{email}/password")
    public ResponseEntity<String> updateEmployeePasswordBody(@PathVariable("email") String email, @Valid @RequestBody String newPasswordBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        String newPassword;
        try {
            JsonNode rootNode = objectMapper.readTree(newPasswordBody);
            newPassword = rootNode.get("newPassword").asText();
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid JSON format", HttpStatus.BAD_REQUEST);
        }

//        System.out.println("Password: " + newPassword);


//        System.out.println(newPassword);
        if (!isValidPassword(newPassword)) {
//            System.out.println(!isValidPassword(newPassword));
            return new ResponseEntity<>("Password must be at least 8 characters long and contain uppercase, lowercase, and digits.", HttpStatus.BAD_REQUEST);
        }

        int result = employeeRepository.updateEmployeePassword(email, newPassword);
        if (result == 1) {
            return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update password", HttpStatus.NOT_FOUND);
        }
    }

    // Handle validation errors globally for this controller
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.append(errorMessage).append("; ");
        });
        return new ResponseEntity<>(errors.toString(), HttpStatus.BAD_REQUEST);
    }


}
