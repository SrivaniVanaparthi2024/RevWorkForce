package com.revworkforce.dao;

import com.revworkforce.model.LeaveRequest;
import com.revworkforce.util.DBUtil;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for LEAVE_REQUEST table
 * Handles only JDBC operations
 */
public class LeaveRequestDAO {

    // ---------------- APPLY LEAVE ----------------
    public void applyLeave(LeaveRequest leave) throws DatabaseException {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "INSERT INTO leave_request " +
            "(leave_req_id, emp_id, leave_type_id, from_date, to_date, reason, status) " +
            "VALUES (leave_request_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, leave.getEmpId());
            ps.setInt(2, leave.getLeaveTypeId());
            ps.setDate(3, new java.sql.Date(leave.getFromDate().getTime()));
            ps.setDate(4, new java.sql.Date(leave.getToDate().getTime()));
            ps.setString(5, leave.getReason());
            ps.setString(6, leave.getStatus());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error applying leave", e);
        } finally {
            close(ps, null);
        }
    }

    // ---------------- VIEW LEAVES BY EMPLOYEE ----------------
    public List<LeaveRequest> getLeavesByEmpId(int empId) throws DatabaseException {

        List<LeaveRequest> list = new ArrayList<LeaveRequest>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT * FROM leave_request " +
            "WHERE emp_id = ? " +
            "ORDER BY leave_req_id DESC";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);

            rs = ps.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeaveReqId(rs.getInt("leave_req_id"));
                lr.setEmpId(rs.getInt("emp_id"));
                lr.setLeaveTypeId(rs.getInt("leave_type_id"));
                lr.setFromDate(rs.getDate("from_date"));
                lr.setToDate(rs.getDate("to_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));

                list.add(lr);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching leave history", e);
        } finally {
            close(ps, rs);
        }

        return list;
    }

    // ---------------- VIEW PENDING LEAVES (MANAGER) ----------------
    public List<LeaveRequest> getPendingLeaves() throws DatabaseException {

        List<LeaveRequest> list = new ArrayList<LeaveRequest>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT * FROM leave_request WHERE status = 'PENDING'";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setLeaveReqId(rs.getInt("leave_req_id"));
                lr.setEmpId(rs.getInt("emp_id"));
                lr.setLeaveTypeId(rs.getInt("leave_type_id"));
                lr.setFromDate(rs.getDate("from_date"));
                lr.setToDate(rs.getDate("to_date"));
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));

                list.add(lr);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching pending leaves", e);
        } finally {
            close(ps, rs);
        }

        return list;
    }

    // ---------------- UPDATE LEAVE STATUS ----------------
    public void updateLeaveStatus(int leaveReqId, String status)
            throws DatabaseException {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "UPDATE leave_request SET status = ? WHERE leave_req_id = ?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, leaveReqId);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating leave status", e);
        } finally {
            close(ps, null);
        }
    }

    // ---------------- CLOSE RESOURCES ----------------
    private void close(PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
    }
}

