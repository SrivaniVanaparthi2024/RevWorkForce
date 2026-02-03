package com.revworkforce.dao.impl;

import com.revworkforce.dao.PayrollDAO;
import com.revworkforce.model.Payroll;
import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAOImpl implements PayrollDAO{
    private Connection conn;

    public PayrollDAOImpl() {
        conn = DBUtil.getConnection();
    }

    public boolean addPayroll(Payroll payroll) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO payroll (payroll_id, emp_id, basic_salary, allowances, deductions) " +
                    "VALUES (payroll_seq.NEXTVAL, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, payroll.getEmpId());
            ps.setDouble(2, payroll.getBasicSalary());
            ps.setDouble(3, payroll.getAllowances());
            ps.setDouble(4, payroll.getDeductions());
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            System.out.println("Error adding payroll: " + e.getMessage());
            return false;
        } finally {
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
    }

    public Payroll getPayrollByEmpId(int empId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Payroll p = null;
        try {
            String sql = "SELECT * FROM payroll WHERE emp_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();
            if(rs.next()) {
                p = new Payroll(
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions")
                );
            }
        } catch(SQLException e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
        return p;
    }

    public List<Payroll> getAllPayroll() {
        List<Payroll> list = new ArrayList<Payroll>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM payroll";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Payroll p = new Payroll(
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions")
                );
                list.add(p);
            }
        } catch(SQLException e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(stmt != null) stmt.close(); } catch(Exception e) {}
        }
        return list;
    }
}
