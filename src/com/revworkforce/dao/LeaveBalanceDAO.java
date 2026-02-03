package com.revworkforce.dao;

import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.LeaveBalance;

import java.util.List;

public interface LeaveBalanceDAO {

    List<LeaveBalance> getBalanceByEmpId(int empId) throws DatabaseException;
}