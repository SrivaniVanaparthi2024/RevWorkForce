package com.revworkforce.dao;


import com.revworkforce.model.Payroll;
import com.revworkforce.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    private Connection conn = DBUtil.getConnection();

    public boolean addPayroll(Payroll payroll) {
        String sql = "INSERT INTO payroll (payroll_id, emp_id, basic_salary, allowances, deductions) " +
                     "VALUES (pay_seq.NEXTVAL, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payroll.getEmpId());
            ps.setDouble(2, payroll.getBasicSalary());
            ps.setDouble(3, payroll.getAllowances());
            ps.setDouble(4, payroll.getDeductions());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding payroll: " + e.getMessage());
            return false;
        }
    }

    public Payroll getPayrollByEmpId(int empId) {
        String sql = "SELECT * FROM payroll WHERE emp_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Payroll(
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
        }
        return null;
    }

    public List<Payroll> getAllPayroll() {
        List<Payroll> list = new ArrayList<>();
        String sql = "SELECT * FROM payroll";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Payroll(
                        rs.getInt("payroll_id"),
                        rs.getInt("emp_id"),
                        rs.getDouble("basic_salary"),
                        rs.getDouble("allowances"),
                        rs.getDouble("deductions")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
        }
        return list;
    }
}

