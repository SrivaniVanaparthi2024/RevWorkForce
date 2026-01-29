package com.revworkforce.model;

public class User {

    private int empId;
    private String username;
    private String role;

    public User(int empId, String username, String role) {
        this.empId = empId;
        this.username = username;
        this.role = role;
    }

    public int getEmpId() {
        return empId;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
