package com.project.poshmaal_task2.repository;

import com.project.poshmaal_task2.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepositoryImpl implements EmployeeRepository{

    private final String FIND_ALL_EMPLOYEES_SQL = "SELECT * FROM Employee";
    private final String FIND_EMPLOYEE_BY_EMAIL_SQL = "SELECT * FROM Employee WHERE email = ?";
    private final String ADD_EMPLOYEE_SQL = "INSERT INTO Employee (email, firstname, lastname, password, locked, role) VALUES (?, ?, ?, ?, ?, ?)";
    private final String UPDATE_EMPLOYEE_SQL = "UPDATE Employee SET firstname = ?, lastname = ?, password = ?, locked = ?, role = ? WHERE email = ?";
    private final String DELETE_EMPLOYEE_SQL = "DELETE FROM Employee WHERE email = ?";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Employee> findAllEmployees() {
        return jdbcTemplate.query(FIND_ALL_EMPLOYEES_SQL, new EmployeeRowMapper());
    }

    @Override
    public Employee findEmployee(String  email) {
        return jdbcTemplate.queryForObject(FIND_EMPLOYEE_BY_EMAIL_SQL, new EmployeeRowMapper(), email);
    }

    @Override
    public int addEmployee(Employee employee) {
        return jdbcTemplate.update(ADD_EMPLOYEE_SQL, employee.getEmail(), employee.getFirstName(), employee.getLastName(), employee.getPassword(), employee.isLocked(), employee.getRole());
    }

    @Override
    public int deleteEmployee(String email) {
        return jdbcTemplate.update(DELETE_EMPLOYEE_SQL, email);
    }

    @Override
    public Employee updateEmployee(String email, Employee employee) {
        int result = jdbcTemplate.update(UPDATE_EMPLOYEE_SQL, employee.getFirstName(), employee.getLastName(), employee.getPassword(), employee.isLocked(), employee.getRole(), email);
        return result == 1 ? employee : null;
    }


    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setEmail(rs.getString("email"));
            employee.setFirstName(rs.getString("firstname"));
            employee.setLastName(rs.getString("lastname"));
            employee.setPassword(rs.getString("password"));
            employee.setLocked(rs.getBoolean("locked"));
            employee.setRole(rs.getString("role"));
            return employee;
        }
    }
}
