package com.revworkforce.dao;

import com.revworkforce.model.LeaveRequest;
import com.revworkforce.exception.DatabaseException;

import java.util.List;

public interface LeaveRequestDAO {

    void applyLeave(LeaveRequest leave) throws DatabaseException;

    List<LeaveRequest> getLeavesByEmpId(int empId)
            throws DatabaseException;

    List<LeaveRequest> getPendingLeavesByManager(int managerId)
            throws DatabaseException;

    void updateLeaveStatus(int leaveReqId, String status)
            throws DatabaseException;
}