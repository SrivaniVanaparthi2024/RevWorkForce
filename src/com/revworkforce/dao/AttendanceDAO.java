package com.revworkforce.dao;

import com.revworkforce.model.Attendance;
import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    private Connection conn;

    public AttendanceDAO() {
        conn = DBUtil.getConnection();
    }

    public boolean addAttendance(Attendance att) {
        PreparedStatement ps = null;
        try {
            String sql =
                "INSERT INTO attendance (attendance_id, emp_id, attendance_date, status) " +
                "VALUES (attendance_seq.NEXTVAL, ?, ?, ?)";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, att.getEmpId());
            ps.setDate(2, att.getDate());
            ps.setString(3, att.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding attendance: " + e.getMessage());
            return false;
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }
    }

    public List<Attendance> getAttendanceByEmpId(int empId) {
        List<Attendance> list = new ArrayList<Attendance>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM attendance WHERE emp_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getDate("attendance_date"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception e) {}
            try { if (ps != null) ps.close(); } catch(Exception e) {}
        }
        return list;
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<Attendance>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM attendance";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Attendance a = new Attendance(
                        rs.getInt("attendance_id"),
                        rs.getInt("emp_id"),
                        rs.getDate("attendance_date"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch(Exception e) {}
            try { if (stmt != null) stmt.close(); } catch(Exception e) {}
        }
        return list;
    }
}
