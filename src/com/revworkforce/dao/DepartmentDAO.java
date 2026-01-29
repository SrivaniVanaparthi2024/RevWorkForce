package com.revworkforce.dao;



import com.revworkforce.model.Department;
import com.revworkforce.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private Connection conn = DBUtil.getConnection();

    public boolean addDepartment(Department dept) {
        String sql = "INSERT INTO department (dept_id, dept_name, location) VALUES (dept_seq.NEXTVAL, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dept.getDeptName());
            ps.setString(2, dept.getLocation());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
            return false;
        }
    }

    public boolean updateDepartment(Department dept) {
        String sql = "UPDATE departments SET dept_name=?, location=? WHERE dept_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dept.getDeptName());
            ps.setString(2, dept.getLocation());
            ps.setInt(3, dept.getDeptId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating department: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDepartment(int deptId) {
        String sql = "DELETE FROM departments WHERE dept_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting department: " + e.getMessage());
            return false;
        }
    }

    public Department getDepartmentById(int deptId) {
        String sql = "SELECT * FROM departments WHERE dept_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, deptId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("location")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching department: " + e.getMessage());
        }
        return null;
    }

    public List<Department> getAllDepartments() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Department(
                        rs.getInt("dept_id"),
                        rs.getString("dept_name"),
                        rs.getString("location")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching departments: " + e.getMessage());
        }
        return list;
    }
}

