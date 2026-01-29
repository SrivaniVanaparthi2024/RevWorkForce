package com.revworkforce.dao;


import com.revworkforce.model.Attendance;
import com.revworkforce.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private Connection conn = DBUtil.getConnection();

    public boolean addAttendance(Attendance att) {
        String sql = "INSERT INTO attendance (attendance_id, emp_id, att_date, status) " +
                     "VALUES (att_seq.NEXTVAL, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, att.getEmpId());
            ps.setDate(2, att.getDate());
            ps.setString(3, att.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding attendance: " + e.getMessage());
            return false;
        }
    }

    public List<Attendance> getAttendanceByEmpId(int empId) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance WHERE emp_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getDate("att_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        }
        return list;
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM attendance";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getDate("att_date"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        }
        return list;
    }
}

