package com.revworkforce.service;



import com.revworkforce.dao.AttendanceDAO;
import com.revworkforce.model.Attendance;
import com.revworkforce.auth.Session;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class AttendanceService {
    private static AttendanceDAO attDAO = new AttendanceDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void manageAttendance() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Attendance Management ---");
            System.out.println("1. Add Attendance");
            System.out.println("2. View All Attendance");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: "); int choice = sc.nextInt(); sc.nextLine();

            switch(choice) {
                case 1: addAttendance(); break;
                case 2: viewAttendance(); break;
                case 3: back = true; break;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private static void addAttendance() {
        System.out.print("Employee ID: "); int empId = sc.nextInt(); sc.nextLine();
        System.out.print("Date (yyyy-mm-dd): "); String d = sc.nextLine();
        System.out.print("Status (Present/Absent): "); String status = sc.nextLine();
        Attendance att = new Attendance(0, empId, Date.valueOf(d), status);
        if(attDAO.addAttendance(att)) System.out.println("✅ Attendance added!");
        else System.out.println("❌ Failed to add attendance.");
    }

    public static void viewAttendance() {
        List<Attendance> list;
        String role = Session.getCurrentUser().getRole();
        if(role.equalsIgnoreCase("Employee")) {
            list = attDAO.getAttendanceByEmpId(Session.getCurrentUser().getUserId());
        } else {
            list = attDAO.getAllAttendance();
        }
        System.out.println("\nID\tEmpID\tDate\tStatus");
        for(Attendance a: list) {
            System.out.println(a.getAttendanceId() + "\t" + a.getEmpId() + "\t" + a.getDate() + "\t" + a.getStatus());
        }
    }
}

