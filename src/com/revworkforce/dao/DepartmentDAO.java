package com.revworkforce.dao;

import com.revworkforce.model.Department;
import java.util.List;

public interface DepartmentDAO {

    boolean addDepartment(Department department);

    boolean updateDepartment(Department department);

    boolean deleteDepartment(int deptId);

    Department getDepartmentById(int deptId);

    List<Department> getAllDepartments();
}