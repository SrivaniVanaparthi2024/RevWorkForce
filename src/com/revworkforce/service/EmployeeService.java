package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;

import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    // -------- AUTHENTICATE USER --------
    public Employee authenticate(String email, String password)
            throws EmployeeNotFoundException, DatabaseException {
        if (email == null || email.trim().length() == 0 ||
            password == null || password.trim().length() == 0) {
            throw new IllegalArgumentException("Email or password cannot be empty");
        }

        Employee emp = employeeDAO.getEmployeeByEmailAndPassword(email, password);
        if (emp == null) {
            throw new EmployeeNotFoundException("Invalid email or password.");
        }
        return emp;
    }

    // -------- GET ALL EMPLOYEES --------
    public List<Employee> getAllEmployees() throws EmployeeNotFoundException, DatabaseException {
        return employeeDAO.getAllEmployees();
    }

    // -------- GET EMPLOYEE BY ID --------
    public Employee getEmployeeById(int empId) throws EmployeeNotFoundException, DatabaseException {
        if (empId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }
        return employeeDAO.getEmployeeById(empId);
    }

    // -------- ADD EMPLOYEE --------
    public void addEmployee(Employee emp) throws DatabaseException {
        if (emp == null) {
            throw new IllegalArgumentException("Employee data cannot be null");
        }
        employeeDAO.addEmployee(emp);
    }

    // -------- UPDATE EMPLOYEE --------
    public void updateEmployee(Employee emp) throws EmployeeNotFoundException, DatabaseException {
        if (emp == null || emp.getEmpId() <= 0) {
            throw new IllegalArgumentException("Invalid employee data");
        }
        // Ensure employee exists before updating
        employeeDAO.getEmployeeById(emp.getEmpId());
        employeeDAO.updateEmployee(emp);
    }

    // -------- DEACTIVATE EMPLOYEE --------
    public void deactivateEmployee(int empId) throws EmployeeNotFoundException, DatabaseException {
        employeeDAO.getEmployeeById(empId);
        employeeDAO.changeEmployeeStatus(empId, "INACTIVE");
    }

    // -------- ACTIVATE EMPLOYEE --------
    public void activateEmployee(int empId) throws EmployeeNotFoundException, DatabaseException {
        employeeDAO.getEmployeeById(empId);
        employeeDAO.changeEmployeeStatus(empId, "ACTIVE");
    }

    // -------- SEARCH EMPLOYEES --------
    public List<Employee> searchEmployees(String keyword) throws EmployeeNotFoundException, DatabaseException {
        if (keyword == null || keyword.trim().length() == 0) {
            throw new IllegalArgumentException("Search keyword cannot be empty");
        }
        return employeeDAO.searchEmployees(keyword);
    }
}
