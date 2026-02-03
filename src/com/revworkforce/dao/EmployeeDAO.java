package com.revworkforce.dao;

import com.revworkforce.model.Employee;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import java.util.List;

public interface EmployeeDAO {
    List<Employee> getAllEmployees() throws DatabaseException, EmployeeNotFoundException;
    Employee getEmployeeById(int empId) throws DatabaseException, EmployeeNotFoundException;
    void addEmployee(Employee emp) throws DatabaseException;
    void updateEmployee(Employee emp) throws DatabaseException;
    List<Employee> searchEmployees(String keyword) throws DatabaseException, EmployeeNotFoundException;
    void changeEmployeeStatus(int empId, String status) throws DatabaseException;
    List<Employee> findByManager(int managerId) throws DatabaseException;
    Employee getEmployeeByEmailAndPassword(String email, String password) throws DatabaseException;
}
