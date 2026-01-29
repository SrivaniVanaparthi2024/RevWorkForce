package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.model.Employee;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.service.LeaveService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

    private Scanner sc = new Scanner(System.in);
    private LeaveService leaveService = new LeaveService();

    public void showMenu() {

        Employee user = Session.getCurrentUser();
        if (user == null) {
            System.out.println(" No active session!");
            return;
        }

        boolean exit = false;

        while (!exit) {
            System.out.println("\n👤 EMPLOYEE MENU");
            System.out.println("1. Apply Leave");
            System.out.println("2. View My Leaves");
            System.out.println("3. View Profile");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            if ("1".equals(choice)) {
                applyLeave(user);
            }
            else if ("2".equals(choice)) {
                viewMyLeaves(user);
            }
            else if ("3".equals(choice)) {
                viewProfile(user);
            }
            else if ("4".equals(choice)) {
                Session.logout();
                exit = true;
            }
            else {
                System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ---------------- APPLY LEAVE ----------------
    private void applyLeave(Employee user) {
        try {
            LeaveRequest lr = new LeaveRequest();
            lr.setEmpId(user.getEmpId());

            System.out.print("Leave Type ID: ");
            lr.setLeaveTypeId(Integer.parseInt(sc.nextLine()));

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.print("From Date (yyyy-MM-dd): ");
            Date fromDate = sdf.parse(sc.nextLine());

            System.out.print("To Date (yyyy-MM-dd): ");
            Date toDate = sdf.parse(sc.nextLine());

            System.out.print("Reason: ");
            String reason = sc.nextLine();

            lr.setFromDate(fromDate);
            lr.setToDate(toDate);
            lr.setReason(reason);

            leaveService.applyLeave(lr);
            System.out.println("✅ Leave applied successfully");

        } catch (InvalidInputException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Invalid date format. Use yyyy-MM-dd");
        }
    }

    // ---------------- VIEW MY LEAVES ----------------
    private void viewMyLeaves(Employee user) {
        try {
            List<LeaveRequest> leaves =
                    leaveService.viewMyLeaves(user.getEmpId());

            if (leaves.isEmpty()) {
                System.out.println("⚠️ No leave records found.");
                return;
            }

            System.out.println(
                "\nREQ_ID | TYPE_ID | FROM | TO | STATUS | REASON"
            );

            for (LeaveRequest lr : leaves) {
                System.out.println(
                    lr.getLeaveReqId() + " | " +
                    lr.getLeaveTypeId() + " | " +
                    lr.getFromDate() + " | " +
                    lr.getToDate() + " | " +
                    lr.getStatus() + " | " +
                    lr.getReason()
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching leaves: " + e.getMessage());
        }
    }

    // ---------------- VIEW PROFILE ----------------
    private void viewProfile(Employee user) {
        System.out.println("\n--- PROFILE ---");
        System.out.println("ID    : " + user.getEmpId());
        System.out.println("Name  : " + user.getEmpName());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Role  : " + user.getRole());
        System.out.println("Status: " + user.getStatus());
    }
}
