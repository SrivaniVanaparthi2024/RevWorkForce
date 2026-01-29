package com.revworkforce.model;



import java.sql.Date;

public class Attendance {
    private int attendanceId;
    private int empId;
    private Date date;
    private String status;

    public Attendance(int attendanceId, int empId, Date date, String status) {
        this.attendanceId = attendanceId;
        this.empId = empId;
        this.date = date;
        this.status = status;
    }

    public int getAttendanceId() { return attendanceId; }
    public int getEmpId() { return empId; }
    public Date getDate() { return date; }
    public String getStatus() { return status; }

    public void setEmpId(int empId) { this.empId = empId; }
    public void setDate(Date date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
}

