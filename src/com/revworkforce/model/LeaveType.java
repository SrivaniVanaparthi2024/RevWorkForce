package com.revworkforce.model;

public class LeaveType {

    private int leaveTypeId;
    private String leaveName;
    private int maxDays;

    public LeaveType() {
    }

    public LeaveType(int leaveTypeId, String leaveName, int maxDays) {
        this.leaveTypeId = leaveTypeId;
        this.leaveName = leaveName;
        this.maxDays = maxDays;
    }

    public int getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(int leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveName() {
        return leaveName;
    }

    public void setLeaveName(String leaveName) {
        this.leaveName = leaveName;
    }

    public int getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(int maxDays) {
        this.maxDays = maxDays;
    }
}
