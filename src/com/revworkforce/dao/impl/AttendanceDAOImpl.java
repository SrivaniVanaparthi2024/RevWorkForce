package com.revworkforce.dao.impl;

import com.revworkforce.dao.AttendanceDAO;
import com.revworkforce.model.Attendance;

import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    private Connection conn;

    public AttendanceDAOImpl() {
       this.conn = DBUtil.getConnection();
    }

    public boolean addAttendance(Attendance att) {
        PreparedStatement preparestatement = null;
        try {
            String sql =
                "INSERT INTO attendance (attendance_id, emp_id, attendance_date, status) " +
                "VALUES (attendance_seq.NEXTVAL, ?, ?, ?)";

            preparestatement = conn.prepareStatement(sql);
            preparestatement.setInt(1, att.getEmpId());
            preparestatement.setDate(2, att.getDate());
            preparestatement.setString(3, att.getStatus());

            return preparestatement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding attendance: " + e.getMessage());
            return false;
        } finally {
            try { if (preparestatement != null) preparestatement.close(); } catch (Exception e) {}
        }
    }

    public List<Attendance> getAttendanceByEmpId(int empId) {
        List<Attendance> list = new ArrayList<Attendance>();
        PreparedStatement preparestatement = null;
        ResultSet resultset = null;
        try {
            String sql = "SELECT * FROM attendance WHERE emp_id=?";
            preparestatement = conn.prepareStatement(sql);
            preparestatement.setInt(1, empId);
            resultset = preparestatement.executeQuery();
            while (resultset.next()) {
                Attendance a = new Attendance(
                        resultset.getInt("attendance_id"),
                        resultset.getInt("emp_id"),
                        resultset.getDate("attendance_date"),
                        resultset.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        } finally {
            try { if (resultset != null) resultset.close(); } catch(Exception e) {}
            try { if (preparestatement != null) preparestatement.close(); } catch(Exception e) {}
        }
        return list;
    }

    public List<Attendance> getAllAttendance() {
        List<Attendance> list = new ArrayList<Attendance>();
        Statement statement = null;
        ResultSet resultset = null;
        try {
            String sql = "SELECT * FROM attendance";
            statement = conn.createStatement();
            resultset = statement.executeQuery(sql);
            while (resultset.next()) {
                Attendance a = new Attendance(
                        resultset.getInt("attendance_id"),
                        resultset.getInt("emp_id"),
                        resultset.getDate("attendance_date"),
                        resultset.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendance: " + e.getMessage());
        } finally {
            try { if (resultset != null) resultset.close(); } catch(Exception e) {}
            try { if (statement != null) statement.close(); } catch(Exception e) {}
        }
        return list;
    }
}
