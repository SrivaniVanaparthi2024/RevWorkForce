package com.revworkforce.dao;

import com.revworkforce.model.Department;
import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private Connection conn;

    public DepartmentDAO() {
        conn = DBUtil.getConnection();
    }

    public boolean addDepartment(Department dept) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO department (dept_id, dept_name, status) VALUES (dept_seq.NEXTVAL, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dept.getDeptName());
            ps.setString(2, dept.getStatus());
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
    }

    public boolean updateDepartment(Department dept) {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE department SET dept_name=?, status=? WHERE dept_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dept.getDeptName());
            ps.setString(2, dept.getStatus());
            ps.setInt(3, dept.getDeptId());
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error updating department: " + e.getMessage());
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
    }

    public boolean deleteDepartment(int deptId) {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM department WHERE dept_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptId);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error deleting department: " + e.getMessage());
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
    }

    public Department getDepartmentById(int deptId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Department dept = null;
        try {
            String sql = "SELECT * FROM department WHERE dept_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, deptId);
            rs = ps.executeQuery();
            if(rs.next()) {
                dept = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("status")
                );
            }
        } catch(SQLException e) {
            System.out.println("Error fetching department: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
        return dept;
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<Department>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM department";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Department dept = new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("status")
                );
                list.add(dept);
            }
        } catch(SQLException e) {
            System.out.println("Error fetching departments: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(stmt != null) stmt.close(); } catch(Exception e) {}
        }
        return list;
    }
}
