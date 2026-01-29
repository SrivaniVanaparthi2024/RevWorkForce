package com.revworkforce.dao;

import com.revworkforce.model.LeaveBalance;
import com.revworkforce.util.DBUtil;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LeaveBalanceDAO {

    // -------- GET BALANCE BY EMPLOYEE --------
    public List<LeaveBalance> getBalanceByEmpId(int empId)
            throws DatabaseException {

        List<LeaveBalance> list = new ArrayList<LeaveBalance>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT lb.emp_id, lb.leave_type_id, lb.available_days " +
            "FROM leave_balance lb " +
            "WHERE lb.emp_id = ?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {
                LeaveBalance lb = new LeaveBalance();
                lb.setEmpId(rs.getInt("emp_id"));
                lb.setLeaveTypeId(rs.getInt("leave_type_id"));
                lb.setAvailableDays(rs.getInt("available_days"));
                list.add(lb);
            }

        } catch (Exception e) {
            throw new DatabaseException("Error fetching leave balance", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
        }

        return list;
    }
}
