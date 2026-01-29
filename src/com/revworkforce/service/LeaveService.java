package com.revworkforce.service;

import com.revworkforce.dao.LeaveRequestDAO;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.Date;
import java.util.List;

/**
 * Service class for Leave Management
 */
public class LeaveService {

    private LeaveRequestDAO leaveDAO;

    public LeaveService() {
        this.leaveDAO = new LeaveRequestDAO();
    }

    // ---------------- APPLY LEAVE ----------------
    public void applyLeave(LeaveRequest leave)
            throws DatabaseException, InvalidInputException {

        if (leave == null) {
            throw new InvalidInputException("Leave details cannot be null");
        }

        if (leave.getEmpId() <= 0) {
            throw new InvalidInputException("Invalid employee ID");
        }

        if (leave.getLeaveTypeId() <= 0) {
            throw new InvalidInputException("Invalid leave type");
        }

        Date from = leave.getFromDate();
        Date to = leave.getToDate();

        if (from == null || to == null) {
            throw new InvalidInputException("Leave dates cannot be null");
        }

        if (from.after(to)) {
            throw new InvalidInputException("From date cannot be after To date");
        }

        if (leave.getReason() == null || leave.getReason().trim().length() == 0) {
            throw new InvalidInputException("Leave reason is required");
        }
     // Optional: check overlapping leaves
        List<LeaveRequest> existingLeaves = leaveDAO.getLeavesByEmpId(leave.getEmpId());
        for (LeaveRequest lr : existingLeaves) {
            if (!(leave.getToDate().before(lr.getFromDate()) || leave.getFromDate().after(lr.getToDate()))) {
                throw new InvalidInputException("Leave dates overlap with existing leave request.");
            }
        }

        // default status
        leave.setStatus("PENDING");

        leaveDAO.applyLeave(leave);
    }

    // ---------------- VIEW EMPLOYEE LEAVES ----------------
    public List<LeaveRequest> viewMyLeaves(int empId)
            throws DatabaseException, InvalidInputException {

        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee ID");
        }

        return leaveDAO.getLeavesByEmpId(empId);
    }

    // ---------------- VIEW PENDING LEAVES (MANAGER) ----------------
    public List<LeaveRequest> viewPendingLeaves()
            throws DatabaseException {

        return leaveDAO.getPendingLeaves();
    }

    // ---------------- APPROVE LEAVE ----------------
    public void approveLeave(int leaveReqId)
            throws DatabaseException, InvalidInputException {

        if (leaveReqId <= 0) {
            throw new InvalidInputException("Invalid leave request ID");
        }

        leaveDAO.updateLeaveStatus(leaveReqId, "APPROVED");
    }

    // ---------------- REJECT LEAVE ----------------
    public void rejectLeave(int leaveReqId)
            throws DatabaseException, InvalidInputException {

        if (leaveReqId <= 0) {
            throw new InvalidInputException("Invalid leave request ID");
        }

        leaveDAO.updateLeaveStatus(leaveReqId, "REJECTED");
    }
    

 // ---------------- MANAGER: VIEW TEAM LEAVES ----------------
    public List<LeaveRequest> manageTeamLeaves(int managerId)
            throws DatabaseException {

        // Manager can view all pending leave requests
        // No manager_id mapping exists in DB
        return leaveDAO.getPendingLeaves();
    }

}

