// ----------------- ManagerMenu.java -----------------
package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Employee;
import com.revworkforce.service.LeaveService;
import com.revworkforce.service.AttendanceService;
import com.revworkforce.service.PayrollService;

import java.util.Scanner;

public class ManagerMenu {
    private Scanner sc = new Scanner(System.in);
    private LeaveService leaveService = new LeaveService();
    private AttendanceService attendanceService = new AttendanceService();
    private PayrollService payrollService = new PayrollService();

    public void showMenu() throws DatabaseException {
        Employee user = Session.getCurrentUser();
        if (user == null) { System.out.println("❌ No active session!"); return; }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n📋 MANAGER MENU");
            System.out.println("1. Approve Leaves");
            System.out.println("2. View Team");
            System.out.println("3. View Team Attendance");
            System.out.println("4. View Team Payroll");
            System.out.println("5. Logout");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            if ("1".equals(choice)) leaveService.manageTeamLeaves(user.getEmpId());
            else if ("2".equals(choice)) System.out.println("📌 View Team Module Coming Soon...");
            else if ("3".equals(choice)) attendanceService.viewAttendanceForManager(user.getEmpId());
            else if ("4".equals(choice)) payrollService.viewPayrollForManager(user.getEmpId());
            else if ("5".equals(choice)) { System.out.println("👋 Logging out..."); exit=true; }
            else System.out.println("❌ Invalid choice! Please select 1-5.");
        }
    }
}
