package com.revworkforce.dao;

import com.revworkforce.model.LeaveType;
import java.util.List;

public interface LeaveTypeDAO {

    void addLeaveType(LeaveType leaveType);

    void updateLeaveType(LeaveType leaveType);

    LeaveType getLeaveTypeById(int leaveTypeId);

    List<LeaveType> getAllLeaveTypes();
}

