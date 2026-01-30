package com.revworkforce.service;

import com.revworkforce.dao.AuditLogDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.AuditLog;

import java.util.List;

public class AuditLogService {

    private AuditLogDAO auditLogDAO = new AuditLogDAO();

    public void recordAction(int empId, String action, String module) throws DatabaseException {
        AuditLog log = new AuditLog();
        log.setEmpId(empId);
        log.setAction(action);
        log.setModule(module);

        auditLogDAO.logAction(log);
    }

    public List<AuditLog> viewAuditLogs() throws DatabaseException {
        return auditLogDAO.getLogs();
    }
}
