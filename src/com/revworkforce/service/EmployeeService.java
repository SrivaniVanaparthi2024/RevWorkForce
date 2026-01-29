package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    // -------- LOGIN / AUTHENTICATE --------
    public Employee login(String email, String password)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (email == null || email.trim().length() == 0 || password == null || password.trim().length() == 0) {
            throw new InvalidInputException("Email and password cannot be empty");
        }

        Employee emp = employeeDAO.getEmployeeByEmailAndPassword(email, password);
        if (emp == null) {
            throw new EmployeeNotFoundException("Invalid email or password");
        }
        return emp;
    }

    // -------- GET PROFILE --------
    public Employee getProfile(int empId) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee ID");
        }
        return employeeDAO.getEmployeeById(empId);
    }

    // -------- UPDATE PROFILE --------
    public boolean updateProfile(Employee emp) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (emp == null || emp.getEmpId() <= 0) {
            throw new InvalidInputException("Invalid employee data");
        }
        employeeDAO.updateEmployee(emp);
        return true;
    }

    // -------- GET MANAGER DETAILS --------
    public Employee getManagerDetails(int empId) throws EmployeeNotFoundException, DatabaseException {
        Employee emp = employeeDAO.getEmployeeById(empId);
        if (emp.getManagerId() != null) {
            return employeeDAO.getEmployeeById(emp.getManagerId());
        }
        return null;
    }

    // -------- GET EMPLOYEE DIRECTORY --------
    public List<Employee> getEmployeeDirectory() throws EmployeeNotFoundException, DatabaseException {
        return employeeDAO.getAllEmployees();
    }

    // -------- GET DIRECT REPORTS --------
    public List<Employee> getDirectReportees(int managerId) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (managerId <= 0) {
            throw new InvalidInputException("Invalid manager ID");
        }
        return employeeDAO.findByManager(managerId);
    }

    // -------- ACTIVATE / DEACTIVATE --------
    public boolean deactivateEmployee(int empId) throws EmployeeNotFoundException, DatabaseException {
        employeeDAO.changeEmployeeStatus(empId, "INACTIVE");
        return true;
    }

    public boolean activateEmployee(int empId) throws EmployeeNotFoundException, DatabaseException {
        employeeDAO.changeEmployeeStatus(empId, "ACTIVE");
        return true;
    }

    // -------- ASSIGN MANAGER --------
    public boolean assignManager(int empId, int managerId) throws EmployeeNotFoundException, DatabaseException {
        Employee emp = employeeDAO.getEmployeeById(empId);
        emp.setManagerId(managerId);
        employeeDAO.updateEmployee(emp);
        return true;
    }

    // -------- SEARCH EMPLOYEE --------
    public List<Employee> searchEmployeeByKeyword(String keyword) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (keyword == null || keyword.trim().length() == 0) {
            throw new InvalidInputException("Search keyword cannot be empty");
        }
        return employeeDAO.searchEmployees(keyword);
    }
}
