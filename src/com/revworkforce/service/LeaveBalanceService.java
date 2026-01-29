package com.revworkforce.service;

import com.revworkforce.dao.LeaveBalanceDAO;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

public class LeaveBalanceService {

    private LeaveBalanceDAO balanceDAO;

    public LeaveBalanceService() {
        this.balanceDAO = new LeaveBalanceDAO();
    }

    // -------- VIEW MY LEAVE BALANCE --------
    public List<LeaveBalance> viewMyLeaveBalance(int empId)
            throws DatabaseException, InvalidInputException {

        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee ID");
        }

        return balanceDAO.getBalanceByEmpId(empId);
    }
}

