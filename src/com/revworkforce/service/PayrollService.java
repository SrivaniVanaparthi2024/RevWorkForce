package com.revworkforce.service;



import com.revworkforce.dao.PayrollDAO;
import com.revworkforce.model.Payroll;
import com.revworkforce.auth.Session;
import java.util.List;
import java.util.Scanner;

public class PayrollService {
    private static PayrollDAO payrollDAO = new PayrollDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void managePayroll() {
        boolean back = false;
        while(!back) {
            System.out.println("\n--- Payroll Management ---");
            System.out.println("1. Add Payroll");
            System.out.println("2. View Payrolls");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: "); int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: addPayroll(); break;
                case 2: viewPayrolls(); break;
                case 3: back = true; break;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private static void addPayroll() {
        System.out.print("Employee ID: "); int empId = sc.nextInt();
        System.out.print("Basic Salary: "); double basic = sc.nextDouble();
        System.out.print("Allowances: "); double allowance = sc.nextDouble();
        System.out.print("Deductions: "); double deduction = sc.nextDouble(); sc.nextLine();
        Payroll p = new Payroll(0, empId, basic, allowance, deduction);
        if(payrollDAO.addPayroll(p)) System.out.println("✅ Payroll added successfully!");
        else System.out.println("❌ Failed to add payroll.");
    }

    public static void viewPayrolls() {
        List<Payroll> list;
        String role = Session.getCurrentUser().getRole();
        if(role.equalsIgnoreCase("Employee")) {
            Payroll p = payrollDAO.getPayrollByEmpId(Session.getCurrentUser().getUserId());
            if(p!=null) {
                System.out.println("\nID\tEmpID\tBasic\tAllowances\tDeductions");
                System.out.println(p.getPayrollId() + "\t" + p.getEmpId() + "\t" + p.getBasicSalary() + "\t" + p.getAllowances() + "\t" + p.getDeductions());
            } else System.out.println("No payroll data found.");
        } else {
            list = payrollDAO.getAllPayroll();
            System.out.println("\nID\tEmpID\tBasic\tAllowances\tDeductions");
            for(Payroll p: list) {
                System.out.println(p.getPayrollId() + "\t" + p.getEmpId() + "\t" + p.getBasicSalary() + "\t" + p.getAllowances() + "\t" + p.getDeductions());
            }
        }
    }
}

