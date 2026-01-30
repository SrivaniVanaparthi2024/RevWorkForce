package com.revworkforce.service;

import com.revworkforce.dao.PayrollDAO;
import com.revworkforce.model.Payroll;
import com.revworkforce.auth.Session;
import java.util.List;
import java.util.Scanner;

public class PayrollService {
    private PayrollDAO payrollDAO = new PayrollDAO();
    private Scanner sc = new Scanner(System.in);

    public void managePayroll() {
        boolean back = false;
        while(!back) {
            System.out.println("\n--- Payroll Management ---");
            System.out.println("1. Add Payroll");
            System.out.println("2. View Payrolls");
            System.out.println("3. Back");
            System.out.print("Enter choice: "); 
            int choice = Integer.parseInt(sc.nextLine());

            if(choice == 1) addPayroll();
            else if(choice == 2) viewPayroll();
            else if(choice == 3) back = true;
            else System.out.println("❌ Invalid choice");
        }
    }
    
 // Called from EmployeeMenu
    public void viewPayrollForEmployee(int empId) {
        Payroll p = payrollDAO.getPayrollByEmpId(empId);
        printSinglePayroll(p);
    }

    // Called from ManagerMenu
    public void viewPayrollForManager(int managerId) {
        // No manager mapping → manager views all payrolls
        List<Payroll> list = payrollDAO.getAllPayroll();
        printAllPayroll(list);
    }

    private void addPayroll() {
        System.out.print("Employee ID: "); int empId = sc.nextInt();sc.nextLine();
        System.out.print("Basic Salary: "); double basic = sc.nextDouble();sc.nextLine();
        System.out.print("Allowances: "); double allowance = sc.nextDouble();sc.nextLine();
        System.out.print("Deductions: "); double deduction = sc.nextDouble();sc.nextLine();
        Payroll p = new Payroll(0, empId, basic, allowance, deduction);
        if(payrollDAO.addPayroll(p)) System.out.println("✅ Added successfully");
        else System.out.println("❌ Failed to add");
    }

//    private void viewPayrolls() {
//        String role = Session.getCurrentUser().getRole();
//        if("Employee".equalsIgnoreCase(role)) {
//            Payroll p = payrollDAO.getPayrollByEmpId(Session.getCurrentUser().getEmpId());
//            if(p != null) {
//                System.out.println("\nID\tEmpID\tBasic\tAllowances\tDeductions");
//                System.out.println(p.getPayrollId()+"\t"+p.getEmpId()+"\t"+p.getBasicSalary()+"\t"+p.getAllowances()+"\t"+p.getDeductions());
//            } else System.out.println("No payroll data found");
//        } else {
//            List<Payroll> list = payrollDAO.getAllPayroll();
//            System.out.println("\nID\tEmpID\tBasic\tAllowances\tDeductions");
//            for(Payroll p : list) {
//                System.out.println(p.getPayrollId()+"\t"+p.getEmpId()+"\t"+p.getBasicSalary()+"\t"+p.getAllowances()+"\t"+p.getDeductions());
//            }
//        }
//    }
    
    private void viewPayroll() {
        String role = Session.getCurrentUser().getRole();

        if ("Employee".equalsIgnoreCase(role)) {
            viewPayrollForEmployee(Session.getCurrentUser().getEmpId());
        } else {
            viewPayrollForManager(Session.getCurrentUser().getEmpId());
        }
    }

    private void printSinglePayroll(Payroll p) {
        if (p == null) {
            System.out.println("⚠️ No payroll data found");
            return;
        }

        System.out.println("\nID\tEmpID\tBasic\tAllow\tDeduction");
        System.out.println(
            p.getPayrollId() + "\t" +
            p.getEmpId() + "\t" +
            p.getBasicSalary() + "\t" +
            p.getAllowances() + "\t" +
            p.getDeductions()
        );
    }

    private void printAllPayroll(List<Payroll> list) {
        System.out.println("\nID\tEmpID\tBasic\tAllow\tDeduction");
        for (Payroll p : list) {
            System.out.println(
                p.getPayrollId() + "\t" +
                p.getEmpId() + "\t" +
                p.getBasicSalary() + "\t" +
                p.getAllowances() + "\t" +
                p.getDeductions()
            );
        }
    }
}
