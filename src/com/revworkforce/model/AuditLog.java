package com.revworkforce.model;

import java.util.Date;

public class AuditLog {

    private int logId;
    private int empId;
    private String action;
    private String module;
    private Date actionTime;

    public AuditLog() {
    }

    public AuditLog(int logId, int empId, String action,
                    String module, Date actionTime) {
        this.logId = logId;
        this.empId = empId;
        this.action = action;
        this.module = module;
        this.actionTime = actionTime;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
}

