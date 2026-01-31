
package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Employee;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.service.HolidayService;
import com.revworkforce.service.LeaveService;
import com.revworkforce.service.AttendanceService;
import com.revworkforce.service.PayrollService;
import com.revworkforce.service.PerformanceReviewService;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
    private Scanner sc = new Scanner(System.in);
    private LeaveService leaveService = new LeaveService();
    private AttendanceService attendanceService = new AttendanceService();
    private PayrollService payrollService = new PayrollService();
    private HolidayService holidayservice=new HolidayService();

    public void showMenu() throws DatabaseException {
        Employee user = Session.getCurrentUser();
        if (user == null) { System.out.println("❌ No active session!"); return; }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n MANAGER MENU");
            System.out.println("1. Approve Leaves");
            System.out.println("2. give emp performace review");
            System.out.println("3. View Team Attendance");
            System.out.println("4. View Team Payroll");
            System.out.println("5. view Holidays");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine();


            if ("1".equals(choice)) approveLeaves();
            else if ("2".equals(choice)) giveEmployeePerformanceReview();
            else if ("3".equals(choice)) attendanceService.viewAttendanceForManager(user.getEmpId());
            else if ("4".equals(choice)) payrollService.viewPayrollForManager(user.getEmpId());
            else if ("5".equals(choice)) holidayservice.viewHolidays();
            else if ("6".equals(choice)) { System.out.println("👋 Logging out..."); exit=true; }
            else System.out.println("❌ Invalid choice! Please select 1-5.");
        }
            
    }
    
    private void approveLeaves() {

        Employee manager = Session.getCurrentUser();
        int managerId = manager.getEmpId();

        try {
            List<LeaveRequest> leaves =
                leaveService.viewPendingLeavesForManager(managerId);

            if (leaves.isEmpty()) {
                System.out.println("No pending leave requests.");
                return;
            }

            System.out.println(
                "REQ_ID | EMP_ID | EMP_NAME | FROM | TO | TYPE_ID | REASON | STATUS");

            EmployeeDAO empDAO = new EmployeeDAO();

            for (LeaveRequest lr : leaves) {
                Employee emp = empDAO.getEmployeeById(lr.getEmpId());

                System.out.println(
                    lr.getLeaveReqId() + " | " +
                    emp.getEmpId() + " | " +
                    emp.getEmpName() + " | " +
                    lr.getFromDate() + " | " +
                    lr.getToDate() + " | " +
                    lr.getLeaveTypeId() + " | " +
                    lr.getReason() + " | " +
                    lr.getStatus()
                );
            }

            System.out.print("Enter Leave Request ID: ");
            int reqId = Integer.parseInt(sc.nextLine());

            System.out.print("Approve (A) / Reject (R): ");
            String action = sc.nextLine();

            if ("A".equalsIgnoreCase(action)) {
                leaveService.approveLeave(reqId);
                System.out.println("Leave Approved");
            }
            else if ("R".equalsIgnoreCase(action)) {
                leaveService.rejectLeave(reqId);
                System.out.println("Leave Rejected");
            }
            else {
                System.out.println("Invalid choice");
            }

        } catch (Exception e) {
            System.out.println("Error processing leave approval");
        }
        
        
    }
    private void giveEmployeePerformanceReview() {

        Employee manager = Session.getCurrentUser();

        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Review Year: ");
            int year = Integer.parseInt(sc.nextLine());

            System.out.print("Enter Manager Feedback: ");
            String feedback = sc.nextLine();

            System.out.print("Enter Rating (1-5): ");
            int rating = Integer.parseInt(sc.nextLine());

            PerformanceReviewService service =
                    new PerformanceReviewService();

            service.giveManagerReview(
                    manager.getEmpId(),
                    empId,
                    year,
                    feedback,
                    rating
            );

            System.out.println("Performance review added");

        } catch (Exception e) {
            System.out.println("❌ " + e.getMessage());
        }
    }
    
}
