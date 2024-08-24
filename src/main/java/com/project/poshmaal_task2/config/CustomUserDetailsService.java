package com.project.poshmaal_task2.config;

import com.project.poshmaal_task2.model.Employee;
import com.project.poshmaal_task2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findEmployee(email);
        System.out.println("employee: "+employee);
        System.out.println(employee.getEmail()+" "+employee.getPassword()+" "+employee.getRole());
        if (employee==null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(employee.getEmail(), "{noop}"+employee.getPassword(), employee.getRole());
    }
}
