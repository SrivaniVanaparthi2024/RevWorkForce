package com.revworkforce.service.test;

import com.revworkforce.model.LeaveRequest;
import com.revworkforce.service.LeaveService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Unit test for LeaveService
 */
public class LeaveServiceTest {

    private LeaveService leaveService;
    private LeaveRequest leave;

    @Before
    public void setUp() {
        leaveService = new LeaveService();
        leave = new LeaveRequest();
    }

    // ---------- APPLY LEAVE SUCCESS ----------
    @Test
    public void testApplyLeaveSuccess()
            throws DatabaseException, InvalidInputException {

        leave.setEmpId(1);
        leave.setLeaveTypeId(1);
        leave.setFromDate(new Date());
        leave.setToDate(new Date());
        leave.setReason("Personal work");

        leaveService.applyLeave(leave);

        assertEquals("PENDING", leave.getStatus());
    }

    // ---------- APPLY LEAVE NULL ----------
    @Test(expected = InvalidInputException.class)
    public void testApplyLeaveNull()
            throws DatabaseException, InvalidInputException {

        leaveService.applyLeave(null);
    }

    // ---------- INVALID EMPLOYEE ID ----------
    @Test(expected = InvalidInputException.class)
    public void testApplyLeaveInvalidEmpId()
            throws DatabaseException, InvalidInputException {

        leave.setEmpId(0);
        leave.setLeaveTypeId(1);
        leave.setFromDate(new Date());
        leave.setToDate(new Date());
        leave.setReason("Test");

        leaveService.applyLeave(leave);
    }

    // ---------- FROM DATE AFTER TO DATE ----------
    @Test(expected = InvalidInputException.class)
    public void testInvalidDateRange()
            throws DatabaseException, InvalidInputException {

        leave.setEmpId(1);
        leave.setLeaveTypeId(1);
        leave.setFromDate(new Date(System.currentTimeMillis() + 86400000));
        leave.setToDate(new Date());
        leave.setReason("Wrong date");

        leaveService.applyLeave(leave);
    }

    // ---------- VIEW MY LEAVES ----------
    @Test
    public void testViewMyLeaves()
            throws DatabaseException, InvalidInputException {

        assertNotNull(leaveService.viewMyLeaves(1));
    }

    // ---------- INVALID VIEW MY LEAVES ----------
    @Test(expected = InvalidInputException.class)
    public void testViewMyLeavesInvalidEmpId()
            throws DatabaseException, InvalidInputException {

        leaveService.viewMyLeaves(0);
    }

    // ---------- VIEW PENDING LEAVES ----------
  

    // ---------- APPROVE LEAVE ----------
    @Test
    public void testApproveLeave()
            throws DatabaseException, InvalidInputException {

        leaveService.approveLeave(1);
    }

    // ---------- REJECT LEAVE ----------
    @Test
    public void testRejectLeave()
            throws DatabaseException, InvalidInputException {

        leaveService.rejectLeave(1);
    }

    // ---------- INVALID APPROVE ----------
    @Test(expected = InvalidInputException.class)
    public void testApproveInvalidId()
            throws DatabaseException, InvalidInputException {

        leaveService.approveLeave(0);
    }
}