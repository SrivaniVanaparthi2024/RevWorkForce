package com.revworkforce.dao;

import com.revworkforce.model.AuditLog;
import com.revworkforce.util.DBUtil;
import com.revworkforce.exception.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditLogDAO {

    public int logAction(AuditLog log) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
        	connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(
                "INSERT INTO audit_log VALUES (audit_seq.NEXTVAL, ?, ?, ?, SYSDATE)"
            );
            preparedStatement.setInt(1, log.getEmpId());
            preparedStatement.setString(2, log.getAction());
            preparedStatement.setString(3, log.getModule());

            return preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DatabaseException("Error logging audit action");
        } finally {
            try { if(preparedStatement != null) preparedStatement.close(); } catch(Exception e) {}
            try { if(connection != null) connection.close(); } catch(Exception e) {}
        }
    }

    public List<AuditLog> getLogs() throws DatabaseException {
        List<AuditLog> logs = new ArrayList<AuditLog>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
        	connection = DBUtil.getConnection();
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM audit_log ORDER BY action_time DESC"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                AuditLog log = new AuditLog();
                log.setLogId(resultSet.getInt("log_id"));
                log.setEmpId(resultSet.getInt("emp_id"));
                log.setAction(resultSet.getString("action"));
                log.setModule(resultSet.getString("module"));
                log.setActionTime(resultSet.getDate("action_time"));

                logs.add(log);
            }

        } catch (Exception e) {
            throw new DatabaseException("Error fetching audit logs");
        } finally {
            try { if(preparedStatement != null) preparedStatement.close(); } catch(Exception e) {}
            try { if(resultSet != null) resultSet.close(); } catch(Exception e) {}
            try { if(connection != null) connection.close(); } catch(Exception e) {}
        }

        return logs;
    }
}