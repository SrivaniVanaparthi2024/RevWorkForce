package com.revworkforce.model;

import java.util.Date;

/**
 * Represents LEAVE_REQUEST table
 */
public class LeaveRequest {

    private int leaveReqId;
    private int empId;
    private int leaveTypeId;
    private Date fromDate;
    private Date toDate;
    private String reason;
    private String status;   // PENDING / APPROVED / REJECTED

    // Default constructor
    public LeaveRequest() {}

    // Constructor for insert
    public LeaveRequest(int empId, int leaveTypeId,
                        Date fromDate, Date toDate,
                        String reason, String status) {
        this.empId = empId;
        this.leaveTypeId = leaveTypeId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
    }

    // Getters & Setters
    public int getLeaveReqId() {
        return leaveReqId;
    }

    public void setLeaveReqId(int leaveReqId) {
        this.leaveReqId = leaveReqId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
