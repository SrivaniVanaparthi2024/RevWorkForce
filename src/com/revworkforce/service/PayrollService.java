package com.revworkforce.service;

import com.revworkforce.dao.PayrollDAO;
import com.revworkforce.dao.impl.PayrollDAOImpl;
import com.revworkforce.model.Payroll;
import com.revworkforce.auth.Session;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayrollService {

    private static final Logger logger = LoggerFactory.getLogger(PayrollService.class);

    private PayrollDAO payrollDAO = new PayrollDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public void managePayroll() {
        logger.info("Payroll Management menu opened");
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
            else if(choice == 3) {
                back = true;
                logger.info("Exiting Payroll Management menu");
            }
            else {
                System.out.println("❌ Invalid choice");
                logger.warn("Invalid choice entered in Payroll Management menu: {}", choice);
            }
        }
    }
    
    // Called from EmployeeMenu
    public void viewPayrollForEmployee(int empId) {
        try {
            Payroll p = payrollDAO.getPayrollByEmpId(empId);
            if (p != null) {
                logger.info("Fetched payroll for employee ID={}", empId);
            } else {
                logger.info("No payroll found for employee ID={}", empId);
            }
            printSinglePayroll(p);
        } catch (Exception e) {
            logger.error("Error fetching payroll for employee ID={}", empId, e);
        }
    }

    // Called from ManagerMenu
    public void viewPayrollForManager(int managerId) {
        try {
            List<Payroll> list = payrollDAO.getAllPayroll();
            logger.info("Fetched {} payroll records for manager view", (list != null ? list.size() : 0));
            printAllPayroll(list);
        } catch (Exception e) {
            logger.error("Error fetching payrolls for manager ID={}", managerId, e);
        }
    }

    private void addPayroll() {
        try {
            System.out.print("Employee ID: "); int empId = sc.nextInt(); sc.nextLine();
            System.out.print("Basic Salary: "); double basic = sc.nextDouble(); sc.nextLine();
            System.out.print("Allowances: "); double allowance = sc.nextDouble(); sc.nextLine();
            System.out.print("Deductions: "); double deduction = sc.nextDouble(); sc.nextLine();

            Payroll p = new Payroll(0, empId, basic, allowance, deduction);

            if(payrollDAO.addPayroll(p)) {
                System.out.println("✅ Added successfully");
                logger.info("Payroll added for employee ID={} Basic={} Allowances={} Deductions={}", empId, basic, allowance, deduction);
            } else {
                System.out.println("❌ Failed to add");
                logger.warn("Failed to add payroll for employee ID={}", empId);
            }
        } catch (Exception e) {
            System.out.println("❌ Error occurred while adding payroll");
            logger.error("Exception while adding payroll", e);
        }
    }

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