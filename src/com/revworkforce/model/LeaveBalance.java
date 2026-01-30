package com.revworkforce.model;

public class LeaveBalance {

    private int balanceId;
    private int empId;
    private int leaveTypeId;
    private int availableDays;

    public LeaveBalance() {
    }

    public LeaveBalance(int balanceId, int empId, int leaveTypeId, int availableDays) {
        this.balanceId = balanceId;
        this.empId = empId;
        this.leaveTypeId = leaveTypeId;
        this.availableDays = availableDays;
    }


	public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
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

    public int getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(int availableDays) {
        this.availableDays = availableDays;
    }
}
