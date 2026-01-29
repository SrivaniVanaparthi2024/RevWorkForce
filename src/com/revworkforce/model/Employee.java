package com.revworkforce.model;

import java.util.Date;

/**
 * Employee model represents EMPLOYEE table.
 * Follows JavaBean standards and ER diagram.
 */
public class Employee {

    private int empId;
    private String empName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Date dob;
    private Date joiningDate;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String role;
    private int deptId;
    private int designationId;
    private Integer managerId;
    private String status;

    // ✅ Mandatory default constructor
    public Employee() {
    }

    // ✅ Full constructor (used while INSERT / UPDATE)
    public Employee(int empId, String empName, String email, String password,
                    String phone, String address, Date dob, Date joiningDate,
                    String emergencyContactName, String emergencyContactPhone,
                    String role, int deptId, int designationId,
                    Integer managerId, String status) {

        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.joiningDate = joiningDate;
        this.emergencyContactName = emergencyContactName;
        this.emergencyContactPhone = emergencyContactPhone;
        this.role = role;
        this.deptId = deptId;
        this.designationId = designationId;
        this.managerId = managerId;
        this.status = status;
    }

    // ✅ Lightweight constructor (used for LIST / SEARCH)
    public Employee(int empId, String empName, String email,
                    int deptId, int designationId,
                    String role, String status, Integer managerId) {

        this.empId = empId;
        this.empName = empName;
        this.email = email;
        this.deptId = deptId;
        this.designationId = designationId;
        this.role = role;
        this.status = status;
        this.managerId = managerId;
    }

    // ---------- Getters ----------
    public int getEmpId() { return empId; }
    public String getEmpName() { return empName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public Date getDob() { return dob; }
    public Date getJoiningDate() { return joiningDate; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public String getRole() { return role; }
    public int getDeptId() { return deptId; }
    public int getDesignationId() { return designationId; }
    public Integer getManagerId() { return managerId; }
    public String getStatus() { return status; }

    // ---------- Setters ----------
    public void setEmpId(int empId) { this.empId = empId; }
    public void setEmpName(String empName) { this.empName = empName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setDob(Date dob) { this.dob = dob; }
    public void setJoiningDate(Date joiningDate) { this.joiningDate = joiningDate; }
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }
    public void setRole(String role) { this.role = role; }
    public void setDeptId(int deptId) { this.deptId = deptId; }
    public void setDesignationId(int designationId) {
        this.designationId = designationId;
    }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
    public void setStatus(String status) { this.status = status; }

    // ✅ Useful for debugging & logs
    @Override
    public String toString() {
        return "Employee [empId=" + empId +
               ", name=" + empName +
               ", email=" + email +
               ", role=" + role +
               ", deptId=" + deptId +
               ", designationId=" + designationId +
               ", managerId=" + managerId +
               ", status=" + status + "]";
    }
}
