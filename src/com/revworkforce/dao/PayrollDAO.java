package com.revworkforce.dao;

import com.revworkforce.model.Payroll;
import java.util.List;

public interface PayrollDAO {

    boolean addPayroll(Payroll payroll);

    Payroll getPayrollByEmpId(int empId);

    List<Payroll> getAllPayroll();
}