package com.revworkforce.model;

import java.util.Date;

public class Notification {

    private int notificationId;
    private int empId;
    private String message;
    private String status;
    private Date createdAt;

    public Notification() {
    }

    public Notification(int notificationId, int empId, String message,
                        String status, Date createdAt) {
        this.notificationId = notificationId;
        this.empId = empId;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

