package com.revworkforce.service.test;

import com.revworkforce.dao.impl.EmployeeDAOImpl;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDAOImpl employeeDAOImpl;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee emp;
    private Employee manager;

    @Before
    public void setUp() {
        emp = new Employee();
        emp.setEmpId(101);
        emp.setEmail("user@rev.com");
        emp.setPassword("password123");
        emp.setRole("EMPLOYEE");

        manager = new Employee();
        manager.setEmpId(201);
        manager.setRole("MANAGER");
    }

    // ---------- LOGIN ----------

    @Test
    public void testLoginSuccess()
            throws DatabaseException, EmployeeNotFoundException, InvalidInputException {

        when(employeeDAOImpl.getEmployeeByEmailAndPassword(
                "user@rev.com", "password123"))
                .thenReturn(emp);

        Employee result =
                employeeService.login("user@rev.com", "password123");

        assertNotNull(result);
        assertEquals(101, result.getEmpId());
    }

    @Test(expected = InvalidInputException.class)
    public void testLoginInvalidInput()
            throws Exception {
        employeeService.login("bad", "123");
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testLoginEmployeeNotFound()
            throws Exception {

        when(employeeDAOImpl.getEmployeeByEmailAndPassword(
                "user@rev.com", "password123"))
                .thenReturn(null);

        employeeService.login("user@rev.com", "password123");
    }

    // ---------- PROFILE ----------

    @Test
    public void testGetProfile()
            throws Exception {

        when(employeeDAOImpl.getEmployeeById(101)).thenReturn(emp);

        Employee result = employeeService.getProfile(101);

        assertEquals(101, result.getEmpId());
    }

    @Test(expected = InvalidInputException.class)
    public void testGetProfileInvalidId()
            throws Exception {
        employeeService.getProfile(0);
    }

    // ---------- UPDATE PROFILE ----------

    @Test
    public void testUpdateProfile()
            throws Exception {

        boolean result = employeeService.updateProfile(emp);

        verify(employeeDAOImpl).updateEmployee(emp);
        assertTrue(result);
    }

    // ---------- MANAGER DETAILS ----------

    @Test
    public void testGetManagerDetails()
            throws Exception {

        emp.setManagerId(201);

        when(employeeDAOImpl.getEmployeeById(101)).thenReturn(emp);
        when(employeeDAOImpl.getEmployeeById(201)).thenReturn(manager);

        Employee result = employeeService.getManagerDetails(101);

        assertEquals(201, result.getEmpId());
    }

    // ---------- DIRECT REPORTEES ----------

    @Test
    public void testGetDirectReportees()
            throws Exception {

        when(employeeDAOImpl.findByManager(201))
                .thenReturn(Arrays.asList(emp));

        List<Employee> list =
                employeeService.getDirectReportees(201);

        assertEquals(1, list.size());
    }

    @Test(expected = InvalidInputException.class)
    public void testGetDirectReporteesInvalidManager()
            throws Exception {
        employeeService.getDirectReportees(0);
    }

    // ---------- ACTIVATE / DEACTIVATE ----------

    @Test
    public void testDeactivateEmployee()
            throws Exception {

        boolean result = employeeService.deactivateEmployee(101);

        verify(employeeDAOImpl)
                .changeEmployeeStatus(101, "INACTIVE");
        assertTrue(result);
    }

    @Test
    public void testActivateEmployee()
            throws Exception {

        boolean result = employeeService.activateEmployee(101);

        verify(employeeDAOImpl)
                .changeEmployeeStatus(101, "ACTIVE");
        assertTrue(result);
    }

    // ---------- ASSIGN MANAGER ----------

    @Test
    public void testAssignManager()
            throws Exception {

        when(employeeDAOImpl.getEmployeeById(101)).thenReturn(emp);
        when(employeeDAOImpl.getEmployeeById(201)).thenReturn(manager);

        boolean result =
                employeeService.assignManager(101, 201);

        verify(employeeDAOImpl).updateEmployee(emp);
        assertTrue(result);
    }

    @Test(expected = InvalidInputException.class)
    public void testAssignManagerInvalidRole()
            throws Exception {

        manager.setRole("EMPLOYEE");

        when(employeeDAOImpl.getEmployeeById(101)).thenReturn(emp);
        when(employeeDAOImpl.getEmployeeById(201)).thenReturn(manager);

        employeeService.assignManager(101, 201);
    }

    // ---------- SEARCH ----------

    @Test
    public void testSearchEmployee()
            throws Exception {

        when(employeeDAOImpl.searchEmployees("john"))
                .thenReturn(Arrays.asList(emp));

        List<Employee> list =
                employeeService.searchEmployeeByKeyword("john");

        assertEquals(1, list.size());
    }

    @Test(expected = InvalidInputException.class)
    public void testSearchEmployeeInvalidKeyword()
            throws Exception {
        employeeService.searchEmployeeByKeyword("");
    }
    
   
    //login DB failure
    @Test(expected = DatabaseException.class)
    public void testLoginDatabaseException()
            throws Exception {

        when(employeeDAOImpl.getEmployeeByEmailAndPassword(
                "user@rev.com", "password123"))
                .thenThrow(new DatabaseException("DB error"));

        employeeService.login("user@rev.com", "password123");
    }
    
    //employee profile not found
    @Test(expected = EmployeeNotFoundException.class)
    public void testGetProfileEmployeeNotFound()
            throws Exception {

        when(employeeDAOImpl.getEmployeeById(101)).thenReturn(null);

        employeeService.getProfile(101);
    }
}
