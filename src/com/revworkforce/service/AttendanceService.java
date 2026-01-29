package com.revworkforce.service;

import com.revworkforce.dao.AttendanceDAO;
import com.revworkforce.model.Attendance;
import com.revworkforce.auth.Session;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class AttendanceService {
    private AttendanceDAO attDAO = new AttendanceDAO();
    private Scanner sc = new Scanner(System.in);

    public void manageAttendance() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Attendance Management ---");
            System.out.println("1. Add Attendance");
            System.out.println("2. View All Attendance");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter choice: "); 
            int choice = Integer.parseInt(sc.nextLine());

            if(choice == 1) addAttendance();
            else if(choice == 2) viewAttendance();
            else if(choice == 3) back = true;
            else System.out.println("❌ Invalid choice");
        }
    }

    private void addAttendance() {
        System.out.print("Employee ID: "); int empId = Integer.parseInt(sc.nextLine());
        System.out.print("Date (yyyy-mm-dd): "); String d = sc.nextLine();
        System.out.print("Status (Present/Absent): "); String status = sc.nextLine();
        Attendance att = new Attendance(0, empId, Date.valueOf(d), status);
        if(attDAO.addAttendance(att)) System.out.println("✅ Attendance added!");
        else System.out.println("❌ Failed to add attendance.");
    }

 // Called from EmployeeMenu
    public void viewAttendanceForEmployee(int empId) {
        List<Attendance> list = attDAO.getAttendanceByEmpId(empId);
        printAttendance(list);
    }

    // Called from ManagerMenu
    public void viewAttendanceForManager(int managerId) {
        // No manager mapping in DB → manager views all attendance
        List<Attendance> list = attDAO.getAllAttendance();
        printAttendance(list);
    }
    
    private void viewAttendance() {
        List<Attendance> list;
        String role = Session.getCurrentUser().getRole();
        if("Employee".equalsIgnoreCase(role)) {
            list = attDAO.getAttendanceByEmpId(Session.getCurrentUser().getEmpId());
        } else {
            list = attDAO.getAllAttendance();
        }
//        System.out.println("\nID\tEmpID\tDate\tStatus");
//        for(Attendance a: list) {
//            System.out.println(a.getAttendanceId()+"\t"+a.getEmpId()+"\t"+a.getDate()+"\t"+a.getStatus());
//        }
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
    }
}
