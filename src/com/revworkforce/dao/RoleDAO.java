package com.revworkforce.dao;



import com.revworkforce.model.Role;
import com.revworkforce.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private Connection conn = DBUtil.getConnection();

    public boolean addRole(Role role) {
        String sql = "INSERT INTO designation (role_id, role_name) VALUES (role_seq.NEXTVAL, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
//            ps.setString(2, role.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding role: " + e.getMessage());
            return false;
        }
    }

    public boolean updateRole(Role role) {
        String sql = "UPDATE roles SET role_name=? WHERE role_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, role.getRoleName());
//            ps.setString(2, role.getDescription());
            ps.setInt(3, role.getRoleId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating role: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM roles WHERE role_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting role: " + e.getMessage());
            return false;
        }
    }

    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM roles WHERE role_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("role_id"), rs.getString("role_name"), rs.getString("description"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching role: " + e.getMessage());
        }
        return null;
    }

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM roles";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Role(rs.getInt("role_id"), rs.getString("role_name"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching roles: " + e.getMessage());
        }
        return list;
    }
}

