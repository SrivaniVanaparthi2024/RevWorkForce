package com.revworkforce.dao.impl;

import com.revworkforce.model.Notification;
import com.revworkforce.util.DBUtil;
import com.revworkforce.dao.NotificationDAO;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAOImpl implements NotificationDAO{

    public List<Notification> getNotificationsByEmployeeId(int empId) throws DatabaseException {
        List<Notification> notifications = new ArrayList<Notification>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();

            String sql = "SELECT notification_id, emp_id, message, is_read, created_date " +
                         "FROM notification WHERE emp_id = ? ORDER BY created_date DESC";

            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getInt("notification_id"));
                n.setEmpId(rs.getInt("emp_id"));
                n.setMessage(rs.getString("message"));
                n.setStatus(rs.getString("is_read"));
                n.setCreatedAt(rs.getDate("created_date"));

                notifications.add(n);
            }
        } catch (Exception e) {
            throw new DatabaseException("Error fetching notifications", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return notifications;
    }
}

