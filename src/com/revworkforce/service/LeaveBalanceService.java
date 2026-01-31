package com.revworkforce.service;

import com.revworkforce.dao.LeaveBalanceDAO;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveBalanceService {

    private static final Logger logger = LoggerFactory.getLogger(LeaveBalanceService.class);

    private LeaveBalanceDAO balanceDAO;

    public LeaveBalanceService() {
        this.balanceDAO = new LeaveBalanceDAO();
    }

    // -------- VIEW MY LEAVE BALANCE --------
    public List<LeaveBalance> viewMyLeaveBalance(int empId)
            throws DatabaseException, InvalidInputException {

        if (empId <= 0) {
            logger.warn("Invalid employee ID provided for leave balance: {}", empId);
            throw new InvalidInputException("Invalid employee ID");
        }

        try {
            List<LeaveBalance> balances = balanceDAO.getBalanceByEmpId(empId);
            logger.info("Fetched leave balance for employee ID={}, count={}", empId, (balances != null ? balances.size() : 0));
            return balances;
        } catch (DatabaseException e) {
            logger.error("DatabaseException while fetching leave balance for employee ID={}", empId, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected exception while fetching leave balance for employee ID={}", empId, e);
            throw new DatabaseException("Unexpected error occurred while fetching leave balance");
        }
    }
}