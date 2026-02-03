package com.revworkforce.dao.impl;

import com.revworkforce.dao.DepartmentDAO;
import com.revworkforce.model.Department;
import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAOImpl implements DepartmentDAO{
    private Connection connecction;

    public DepartmentDAOImpl() {
        connecction = DBUtil.getConnection();
    }

    public boolean addDepartment(Department dept) {
        PreparedStatement preparedstatement = null;
        try {
            String sql = "INSERT INTO department (dept_id, dept_name, status) VALUES (dept_seq.NEXTVAL, ?, ?)";
            preparedstatement = connecction.prepareStatement(sql);
            preparedstatement.setString(1, dept.getDeptName());
            preparedstatement.setString(2, dept.getStatus());
            return preparedstatement.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
            return false;
        } finally {
            try { if(preparedstatement != null) preparedstatement.close(); } catch(Exception e) {}
        }
    }

    public boolean updateDepartment(Department dept) {
        PreparedStatement preparedstatement = null;
        try {
            String sql = "UPDATE department SET dept_name=?, status=? WHERE dept_id=?";
            preparedstatement = connecction.prepareStatement(sql);
            preparedstatement.setString(1, dept.getDeptName());
            preparedstatement.setString(2, dept.getStatus());
            preparedstatement.setInt(3, dept.getDeptId());
            return preparedstatement.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error updating department: " + e.getMessage());
            return false;
        } finally {
            try { if(preparedstatement != null) preparedstatement.close(); } catch(Exception e) {}
        }
    }

    public boolean deleteDepartment(int deptId) {
        PreparedStatement preparedstatement = null;
        try {
            String sql = "DELETE FROM department WHERE dept_id=?";
            preparedstatement = connecction.prepareStatement(sql);
            preparedstatement.setInt(1, deptId);
            return preparedstatement.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error deleting department: " + e.getMessage());
            return false;
        } finally {
            try { if(preparedstatement != null) preparedstatement.close(); } catch(Exception e) {}
        }
    }

    public Department getDepartmentById(int deptId) {
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;
        Department dept = null;
        try {
            String sql = "SELECT * FROM department WHERE dept_id=?";
            preparedstatement = connecction.prepareStatement(sql);
            preparedstatement.setInt(1, deptId);
            resultset = preparedstatement.executeQuery();
            if(resultset.next()) {
                dept = new Department(
                        resultset.getInt("dept_id"),
                        resultset.getString("dept_name"),
                        resultset.getString("status")
                );
            }
        } catch(SQLException e) {
            System.out.println("Error fetching department: " + e.getMessage());
        } finally {
            try { if(resultset != null) resultset.close(); } catch(Exception e) {}
            try { if(preparedstatement != null) preparedstatement.close(); } catch(Exception e) {}
        }
        return dept;
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<Department>();
        Statement statement = null;
        ResultSet resultset = null;
        try {
            String sql = "SELECT * FROM department";
            statement = connecction.createStatement();
            resultset = statement.executeQuery(sql);
            while(resultset.next()) {
                Department dept = new Department(
                        resultset.getInt("dept_id"),
                        resultset.getString("dept_name"),
                        resultset.getString("status")
                );
                list.add(dept);
            }
        } catch(SQLException e) {
            System.out.println("Error fetching departments: " + e.getMessage());
        } finally {
            try { if(resultset != null) resultset.close(); } catch(Exception e) {}
            try { if(statement != null) statement.close(); } catch(Exception e) {}
        }
        return list;
    }
}
