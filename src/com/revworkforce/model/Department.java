package com.revworkforce.model;



public class Department {
    private int deptId;
    private String deptName;
    private String status;

    public Department(int deptId, String deptName, String status) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.status = status;
    }

    public int getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getStatus() { return status; }

    public void setDeptName(String deptName) { this.deptName = deptName; }
    public void setStatus(String status) { this.status = status; }
}
