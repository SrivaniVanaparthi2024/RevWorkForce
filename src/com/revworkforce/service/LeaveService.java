package com.revworkforce.service;

import com.revworkforce.dao.LeaveRequestDAO;
import com.revworkforce.dao.impl.LeaveRequestDAOImpl;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeaveService {

    private static final Logger logger =
            LoggerFactory.getLogger(LeaveService.class);

    private LeaveRequestDAO leaveDAO;

    public LeaveService() {
        this.leaveDAO = new LeaveRequestDAOImpl();
    }

    // ---------------- APPLY LEAVE ----------------
    public void applyLeave(LeaveRequest leave)
            throws DatabaseException, InvalidInputException {

        logger.info("Applying leave request");

        if (leave == null) {
            logger.warn("Leave request object is null");
            throw new InvalidInputException("Leave details cannot be null");
        }

        if (leave.getEmpId() <= 0) {
            logger.warn("Invalid employee ID: {}", leave.getEmpId());
            throw new InvalidInputException("Invalid employee ID");
        }

        if (leave.getLeaveTypeId() <= 0) {
            logger.warn("Invalid leave type ID: {}", leave.getLeaveTypeId());
            throw new InvalidInputException("Invalid leave type");
        }

        Date from = leave.getFromDate();
        Date to = leave.getToDate();

        if (from == null || to == null) {
            logger.warn("Leave dates are null");
            throw new InvalidInputException("Leave dates cannot be null");
        }

        if (from.after(to)) {
            logger.warn("From date {} is after To date {}", from, to);
            throw new InvalidInputException("From date cannot be after To date");
        }

        if (leave.getReason() == null || leave.getReason().trim().length() == 0) {
            logger.warn("Leave reason is empty");
            throw new InvalidInputException("Leave reason is required");
        }

        List<LeaveRequest> existingLeaves =
                leaveDAO.getLeavesByEmpId(leave.getEmpId());

        for (LeaveRequest lr : existingLeaves) {
            if (!(to.before(lr.getFromDate()) || from.after(lr.getToDate()))) {
                logger.warn(
                        "Leave overlap detected for empId={} existingLeaveId={}",
                        leave.getEmpId(), lr.getLeaveReqId()
                );
                throw new InvalidInputException(
                        "Leave dates overlap with existing leave request."
                );
            }
        }

        leave.setStatus("PENDING");
        leaveDAO.applyLeave(leave);

        logger.info(
                "Leave applied successfully for empId={} from={} to={}",
                leave.getEmpId(), from, to
        );
    }

    // ---------------- VIEW EMPLOYEE LEAVES ----------------
    public List<LeaveRequest> viewMyLeaves(int empId)
            throws DatabaseException, InvalidInputException {

        if (empId <= 0) {
            logger.warn("Invalid employee ID while viewing leaves: {}", empId);
            throw new InvalidInputException("Invalid employee ID");
        }

        logger.info("Fetching leaves for empId={}", empId);
        return leaveDAO.getLeavesByEmpId(empId);
    }

    // ---------------- VIEW PENDING LEAVES (MANAGER) ----------------
    public List<LeaveRequest> viewPendingLeavesForManager(int managerId)
            throws DatabaseException, InvalidInputException {

        if (managerId <= 0) {
            logger.warn("Invalid manager ID: {}", managerId);
            throw new InvalidInputException("Invalid manager ID");
        }

        logger.info("Fetching pending leaves for managerId={}", managerId);
        return leaveDAO.getPendingLeavesByManager(managerId);
    }

    // ---------------- APPROVE LEAVE ----------------
    public void approveLeave(int leaveReqId)
            throws DatabaseException, InvalidInputException {

        if (leaveReqId <= 0) {
            logger.warn("Invalid leave request ID for approval: {}", leaveReqId);
            throw new InvalidInputException("Invalid leave request ID");
        }

        leaveDAO.updateLeaveStatus(leaveReqId, "APPROVED");
        logger.info("Leave request {} approved", leaveReqId);
    }

    // ---------------- REJECT LEAVE ----------------
    public void rejectLeave(int leaveReqId)
            throws DatabaseException, InvalidInputException {

        if (leaveReqId <= 0) {
            logger.warn("Invalid leave request ID for rejection: {}", leaveReqId);
            throw new InvalidInputException("Invalid leave request ID");
        }

        leaveDAO.updateLeaveStatus(leaveReqId, "REJECTED");
        logger.info("Leave request {} rejected", leaveReqId);
    }

    // ---------------- MANAGER: VIEW TEAM LEAVES ----------------
    public List<LeaveRequest> manageTeamLeaves(int managerId)
            throws DatabaseException {

        logger.info("Manager {} viewing team pending leaves", managerId);
        return leaveDAO.getPendingLeavesByManager(managerId);
    }
}
