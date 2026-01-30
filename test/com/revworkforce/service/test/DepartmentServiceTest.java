package com.revworkforce.service.test;

import com.revworkforce.dao.DepartmentDAO;
import com.revworkforce.model.Department;
import com.revworkforce.service.DepartmentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentDAO deptDAO; // Mocked DAO

    @InjectMocks
    private DepartmentService deptService; // Service with DAO injected

    private Department dept1;
    private Department dept2;

    @Before
    public void setUp() {
        // Sample departments
        dept1 = new Department(1, "HR", "ACTIVE");
        dept2 = new Department(2, "Finance", "INACTIVE");
    }

    @Test
    public void testAddDepartment() {
        when(deptDAO.addDepartment(dept1)).thenReturn(true);

        boolean result = deptDAO.addDepartment(dept1); // call DAO method
        verify(deptDAO).addDepartment(dept1); // verify DAO interaction

        assertTrue("Department should be added successfully", result);
    }

    @Test
    public void testUpdateDepartment() {
        when(deptDAO.updateDepartment(dept1)).thenReturn(true);

        dept1.setDeptName("HR Updated");
        dept1.setStatus("INACTIVE");

        boolean result = deptDAO.updateDepartment(dept1);
        verify(deptDAO).updateDepartment(dept1);

        assertTrue("Department should be updated successfully", result);
    }

    @Test
    public void testDeleteDepartment() {
        when(deptDAO.deleteDepartment(1)).thenReturn(true);

        boolean result = deptDAO.deleteDepartment(1);
        verify(deptDAO).deleteDepartment(1);

        assertTrue("Department should be deleted successfully", result);
    }

    @Test
    public void testGetDepartmentById() {
        when(deptDAO.getDepartmentById(1)).thenReturn(dept1);

        Department fetched = deptDAO.getDepartmentById(1);
        verify(deptDAO).getDepartmentById(1);

        assertNotNull(fetched);
        assertEquals("HR", fetched.getDeptName());
    }

    @Test
    public void testGetAllDepartments() {
        List<Department> deptList = Arrays.asList(dept1, dept2);
        when(deptDAO.getAllDepartments()).thenReturn(deptList);

        List<Department> fetchedList = deptDAO.getAllDepartments();
        verify(deptDAO).getAllDepartments();

        assertEquals(2, fetchedList.size());
        assertEquals("HR", fetchedList.get(0).getDeptName());
        assertEquals("Finance", fetchedList.get(1).getDeptName());
    }
}
