package com.revworkforce.model;



public class Payroll {
    private int payrollId;
    private int empId;
    private double basicSalary;
    private double allowances;
    private double deductions;

    public Payroll(int payrollId, int empId, double basicSalary, double allowances, double deductions) {
        this.payrollId = payrollId;
        this.empId = empId;
        this.basicSalary = basicSalary;
        this.allowances = allowances;
        this.deductions = deductions;
    }

    public int getPayrollId() { return payrollId; }
    public int getEmpId() { return empId; }
    public double getBasicSalary() { return basicSalary; }
    public double getAllowances() { return allowances; }
    public double getDeductions() { return deductions; }

    public void setEmpId(int empId) { this.empId = empId; }
    public void setBasicSalary(double basicSalary) { this.basicSalary = basicSalary; }
    public void setAllowances(double allowances) { this.allowances = allowances; }
    public void setDeductions(double deductions) { this.deductions = deductions; }
}

