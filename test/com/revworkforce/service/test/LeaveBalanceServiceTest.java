package com.revworkforce.service.test;

import com.revworkforce.dao.impl.LeaveBalanceDAOImpl;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.service.LeaveBalanceService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LeaveBalanceServiceTest {

    @Mock
    private LeaveBalanceDAOImpl balanceDAO;

    @InjectMocks
    private LeaveBalanceService leaveBalanceService;

    private LeaveBalance lb1;
    private LeaveBalance lb2;

    @Before
    public void setUp() {
        lb1 = new LeaveBalance(1, 101, 1, 5);
        lb2 = new LeaveBalance(2, 101, 2, 8);
    }

    @Test
    public void testViewMyLeaveBalance_ValidEmpId()
            throws DatabaseException, InvalidInputException {

        List<LeaveBalance> mockList = Arrays.asList(lb1, lb2);
        when(balanceDAO.getBalanceByEmpId(101)).thenReturn(mockList);

        List<LeaveBalance> result =
                leaveBalanceService.viewMyLeaveBalance(101);

        verify(balanceDAO, times(1)).getBalanceByEmpId(101);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test(expected = InvalidInputException.class)
    public void testViewMyLeaveBalance_InvalidEmpId()
            throws DatabaseException, InvalidInputException {

        leaveBalanceService.viewMyLeaveBalance(0);
    }

    @Test
    public void testLeaveBalanceDataIntegrity()
            throws DatabaseException, InvalidInputException {

        when(balanceDAO.getBalanceByEmpId(101))
                .thenReturn(Arrays.asList(lb1));

        List<LeaveBalance> result =
                leaveBalanceService.viewMyLeaveBalance(101);

        LeaveBalance lb = result.get(0);
        assertEquals(101, lb.getEmpId());
        assertEquals(1, lb.getLeaveTypeId());
        assertEquals(5, lb.getAvailableDays());
    }

    @Test(expected = DatabaseException.class)
    public void testViewMyLeaveBalance_DatabaseException()
            throws DatabaseException, InvalidInputException {

        when(balanceDAO.getBalanceByEmpId(101))
                .thenThrow(new DatabaseException("DB error"));

        leaveBalanceService.viewMyLeaveBalance(101);
    }
    
    
}
