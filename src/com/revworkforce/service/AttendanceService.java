package com.revworkforce.service;

import com.revworkforce.dao.AttendanceDAO;
import com.revworkforce.dao.impl.AttendanceDAOImpl;
import com.revworkforce.model.Attendance;
import com.revworkforce.auth.Session;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttendanceService {

    private static final Logger logger =
            LoggerFactory.getLogger(AttendanceService.class);

    private AttendanceDAO attDAO = new AttendanceDAOImpl();
    private Scanner sc = new Scanner(System.in);

    public void manageAttendance() {
        boolean back = false;
        logger.info("Attendance Management menu opened");

        while (!back) {
            try {
                System.out.println("\n--- Attendance Management ---");
                System.out.println("1. Add Attendance");
                System.out.println("2. View All Attendance");
                System.out.println("3. Back to Main Menu");
                System.out.print("Enter choice: ");

                int choice = Integer.parseInt(sc.nextLine());

                if (choice == 1) {
                    addAttendance();
                } else if (choice == 2) {
                    viewAttendance();
                } else if (choice == 3) {
                    back = true;
                    logger.info("Exiting Attendance Management menu");
                } else {
                    logger.warn("Invalid menu choice entered: {}", choice);
                    System.out.println("❌ Invalid choice");
                }

            } catch (NumberFormatException e) {
                logger.warn("Non-numeric menu input", e);
                System.out.println("❌ Please enter a valid number");
            }
        }
    }

    private void addAttendance() {
        try {
            System.out.print("Employee ID: ");
            int empId = Integer.parseInt(sc.nextLine().trim());

            System.out.print("Date (yyyy-MM-dd): ");
            String d = sc.nextLine().trim();

            Date date;
            try {
                date = Date.valueOf(d);
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid date format entered: {}", d);
                System.out.println("❌ Invalid date format. Use yyyy-MM-dd");
                return;
            }

            System.out.print("Status (PRESENT/ABSENT/LEAVE): ");
            String status = sc.nextLine().trim().toUpperCase();

            if (!status.equals("PRESENT")
                    && !status.equals("ABSENT")
                    && !status.equals("LEAVE")) {
                logger.warn("Invalid attendance status: {}", status);
                System.out.println("❌ Invalid status.");
                return;
            }

            Attendance att = new Attendance(0, empId, date, status);

            boolean added = attDAO.addAttendance(att);

            if (added) {
                logger.info("Attendance added successfully for empId={} date={}", empId, date);
                System.out.println("✅ Attendance added!");
            } else {
                logger.error("Failed to add attendance for empId={} date={}", empId, date);
                System.out.println("❌ Failed to add attendance.");
            }

        } catch (NumberFormatException e) {
            logger.warn("Invalid employee ID input", e);
            System.out.println("❌ Employee ID must be a number.");
        } catch (Exception e) {
            logger.error("Unexpected error while adding attendance", e);
            System.out.println("❌ Unexpected error occurred");
        }
    }

    // Called from EmployeeMenu
    public void viewAttendanceForEmployee(int empId) {
        logger.info("Viewing attendance for employeeId={}", empId);
        List<Attendance> list = attDAO.getAttendanceByEmpId(empId);
        printAttendance(list);
    }

    // Called from ManagerMenu
    public void viewAttendanceForManager(int managerId) {
        logger.info("Manager {} viewing all attendance", managerId);
        List<Attendance> list = attDAO.getAllAttendance();
        printAttendance(list);
    }

    private void viewAttendance() {
        try {
            String role = Session.getCurrentUser().getRole();
            logger.info("Viewing attendance for role={}", role);

            List<Attendance> list;

            if ("Employee".equalsIgnoreCase(role)) {
                int empId = Session.getCurrentUser().getEmpId();
                list = attDAO.getAttendanceByEmpId(empId);
            } else {
                list = attDAO.getAllAttendance();
            }

            printAttendance(list);

        } catch (Exception e) {
            logger.error("Error while viewing attendance", e);
            System.out.println("❌ Error fetching attendance");
        }
    }

    private void printAttendance(List<Attendance> list) {
        System.out.println("\nID\tEmpID\tDate\tStatus");

        for (Attendance a : list) {
            System.out.println(
                    a.getAttendanceId() + "\t" +
                    a.getEmpId() + "\t" +
                    a.getDate() + "\t" +
                    a.getStatus()
            );
        }

        logger.info("Displayed {} attendance records", list.size());
    }
}