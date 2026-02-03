
package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.dao.impl.EmployeeDAOImpl;
import com.revworkforce.model.Employee;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeService {

    private static final Logger logger =
            LoggerFactory.getLogger(EmployeeService.class);

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAOImpl();
    }

    // -------- LOGIN / AUTHENTICATE --------
    public Employee login(String email, String password)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        
        logger.info("Attempting login for email={}", email);

        if (email == null || email.trim().length() <= 8 || !email.trim().toLowerCase().endsWith("@rev.com")
                || password == null || password.trim().length() < 6) {
            logger.warn("Invalid email or password format: email={}", email);
            throw new InvalidInputException("Email and password cannot be empty");
        }

        Employee emp = employeeDAO.getEmployeeByEmailAndPassword(email, password);
        if (emp == null) {
            logger.warn("Login failed for email={}", email);
            throw new EmployeeNotFoundException("Invalid email or password");
        }

        logger.info("Login successful for email={}, empId={}", email, emp.getEmpId());
        return emp;
    }

    // -------- GET PROFILE --------
    public Employee getProfile(int empId)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {

        logger.info("Fetching profile for empId={}", empId);

        if (empId <= 0) {
            logger.warn("Invalid employee ID: {}", empId);
            throw new InvalidInputException("Invalid employee ID");
        }

        Employee emp = employeeDAO.getEmployeeById(empId);
        if (emp == null) {
            logger.warn("Employee not found with empId={}", empId);
            throw new EmployeeNotFoundException("Employee not found");
        }

        logger.info("Profile retrieved for empId={}", empId);
        return emp;
    }

    // -------- UPDATE PROFILE --------
    public boolean updateProfile(Employee emp) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (emp == null || emp.getEmpId() <= 0) {
            logger.warn("Invalid employee data for update: empId={}", emp != null ? emp.getEmpId() : null);
            throw new InvalidInputException("Invalid employee data");
        }

        employeeDAO.updateEmployee(emp);
        logger.info("Profile updated for empId={}", emp.getEmpId());
        return true;
    }

    // -------- GET MANAGER DETAILS --------
    public Employee getManagerDetails(int empId) throws EmployeeNotFoundException, DatabaseException {
        Employee emp = employeeDAO.getEmployeeById(empId);
        if (emp.getManagerId() != null) {
            Employee manager = employeeDAO.getEmployeeById(emp.getManagerId());
            logger.info("Manager details fetched for empId={}", empId);
            return manager;
        }
        logger.info("Employee empId={} has no manager assigned", empId);
        return null;
    }

    // -------- GET EMPLOYEE DIRECTORY --------
    public List<Employee> getEmployeeDirectory() throws EmployeeNotFoundException, DatabaseException {
        logger.info("Fetching employee directory");
        return employeeDAO.getAllEmployees();
    }

    // -------- GET DIRECT REPORTS --------
    public List<Employee> getDirectReportees(int managerId) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (managerId <= 0) {
            logger.warn("Invalid manager ID: {}", managerId);
            throw new InvalidInputException("Invalid manager ID");
        }

        logger.info("Fetching direct reports for managerId={}", managerId);
        return employeeDAO.findByManager(managerId);
    }

    // -------- ACTIVATE / DEACTIVATE --------
    public boolean deactivateEmployee(int empId)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {

        if (empId <= 0) {
            logger.warn("Invalid employee ID for deactivation: {}", empId);
            throw new InvalidInputException("Invalid employee ID");
        }

        employeeDAO.changeEmployeeStatus(empId, "INACTIVE");
        logger.info("Employee empId={} deactivated", empId);
        return true;
    }

    public boolean activateEmployee(int empId)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {

        if (empId <= 0) {
            logger.warn("Invalid employee ID for activation: {}", empId);
            throw new InvalidInputException("Invalid employee ID");
        }

        employeeDAO.changeEmployeeStatus(empId, "ACTIVE");
        logger.info("Employee empId={} activated", empId);
        return true;
    }

    // -------- ASSIGN MANAGER --------
    public boolean assignManager(int empId, int managerId)
            throws EmployeeNotFoundException, DatabaseException, InvalidInputException {

        if (empId <= 0 || managerId <= 0) {
            logger.warn("Invalid employee or manager ID: empId={}, managerId={}", empId, managerId);
            throw new InvalidInputException("Invalid employee or manager ID");
        }

        Employee emp = employeeDAO.getEmployeeById(empId);
        Employee manager = employeeDAO.getEmployeeById(managerId);

        if (!"MANAGER".equalsIgnoreCase(manager.getRole())
                && !"ADMIN".equalsIgnoreCase(manager.getRole())) {
            logger.warn("Selected employee is not a manager: empId={}", managerId);
            throw new InvalidInputException("Selected employee is not a manager");
        }

        emp.setManagerId(managerId);
        employeeDAO.updateEmployee(emp);
        logger.info("Manager empId={} assigned to employee empId={}", managerId, empId);
        return true;
    }

    // -------- SEARCH EMPLOYEE --------
    public List<Employee> searchEmployeeByKeyword(String keyword) throws EmployeeNotFoundException, DatabaseException, InvalidInputException {
        if (keyword == null || keyword.trim().length() == 0) {
            logger.warn("Empty search keyword");
            throw new InvalidInputException("Search keyword cannot be empty");
        }

        logger.info("Searching employees with keyword={}", keyword);
        return employeeDAO.searchEmployees(keyword);
    }

    // ---------------- CRUD OPERATIONS ----------------
    public List<Employee> getAllEmployees() throws EmployeeNotFoundException, DatabaseException {
        List<Employee> list = employeeDAO.getAllEmployees();
        if (list.isEmpty()) {
            logger.warn("No employees found");
            throw new EmployeeNotFoundException("No employees found");
        }

        logger.info("Fetched all employees, count={}", list.size());
        return list;
    }

    public Employee getEmployeeById(int empId) throws EmployeeNotFoundException, DatabaseException {
        Employee emp = employeeDAO.getEmployeeById(empId);
        if (emp == null) {
            logger.warn("Employee not found with ID={}", empId);
            throw new EmployeeNotFoundException("Employee not found with ID: " + empId);
        }

        logger.info("Fetched employee empId={}", empId);
        return emp;
    }

    public void addEmployee(Employee emp) throws DatabaseException {
        employeeDAO.addEmployee(emp);
        logger.info("Added new employee empId={}", emp.getEmpId());
    }

    public void updateEmployee(Employee emp) throws DatabaseException {
        employeeDAO.updateEmployee(emp);
        logger.info("Updated employee empId={}", emp.getEmpId());
    }

    public List<Employee> searchEmployees(String keyword) throws EmployeeNotFoundException, DatabaseException {
        List<Employee> list = employeeDAO.searchEmployees(keyword);
        if (list.isEmpty()) {
            logger.warn("No matching employees found for keyword={}", keyword);
            throw new EmployeeNotFoundException("No matching employees found");
        }

        logger.info("Search returned {} employees for keyword={}", list.size(), keyword);
        return list;
    }
}