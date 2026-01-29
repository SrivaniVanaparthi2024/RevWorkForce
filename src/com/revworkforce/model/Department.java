package com.revworkforce.model;



public class Department {
    private int deptId;
    private String deptName;
    private String location;

    public Department(int deptId, String deptName, String location) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.location = location;
    }

    public int getDeptId() { return deptId; }
    public String getDeptName() { return deptName; }
    public String getLocation() { return location; }

    public void setDeptName(String deptName) { this.deptName = deptName; }
    public void setLocation(String location) { this.location = location; }
}

