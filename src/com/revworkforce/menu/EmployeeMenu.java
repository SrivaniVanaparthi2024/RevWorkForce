package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.model.Employee;
import com.revworkforce.model.Goal;
import com.revworkforce.model.LeaveBalance;
import com.revworkforce.model.LeaveRequest;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.model.Notification;
import com.revworkforce.service.AttendanceService;
import com.revworkforce.service.GoalService;
import com.revworkforce.service.HolidayService;
import com.revworkforce.service.LeaveBalanceService;
import com.revworkforce.service.LeaveService;
import com.revworkforce.service.PayrollService;
import com.revworkforce.service.PerformanceReviewService;
import com.revworkforce.service.NotificationService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EmployeeMenu {

	private LeaveService leaveService = new LeaveService();
    private PerformanceReviewService reviewService = new PerformanceReviewService();
    private NotificationService notificationService = new NotificationService();
    private LeaveBalanceService leaveBalanceService = new LeaveBalanceService();
    private AttendanceService attendanceService = new AttendanceService();
    private PayrollService payrollService = new PayrollService();
    private HolidayService holidayservice=new HolidayService();
    private GoalService goalservice=new GoalService();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private java.util.Scanner sc = new java.util.Scanner(System.in);

    public void showMenu() throws InvalidInputException {
        Employee user = Session.getCurrentUser();
        if (user == null) {
            System.out.println("❌ No active session!");
            return;
        }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n👤 EMPLOYEE MENU");
            System.out.println("1. Apply Leave");
            System.out.println("2. View My Leaves");
            System.out.println("3. View Leave Balance");
            System.out.println("4. View Profile");
            System.out.println("5. View Performance Review");
            System.out.println("6. View Notifications");
            System.out.println("7. View My Attendance");
            System.out.println("8. View My Payroll");
            System.out.println("9. view Holidays");
            System.out.println("10. add goals");
            System.out.println("11. update goals");
            System.out.println("12. view goals");
            System.out.println("13. Logout");
            System.out.print("Enter choice: ");
            
            
            String choice = sc.nextLine();
            if (choice == null || choice.trim().isEmpty()) {
                throw new InvalidInputException("Input cannot be empty.");
            }
            
            if ("1".equals(choice)) applyLeave(user);
            else if ("2".equals(choice)) viewMyLeaves(user);
            else if ("3".equals(choice)) viewLeaveBalance(user);
            else if ("4".equals(choice)) viewProfile(user);
            else if ("5".equals(choice)) viewPerformanceReviews(user);
            else if ("6".equals(choice)) viewNotifications(user);
            else if ("7".equals(choice)) attendanceService.viewAttendanceForEmployee(user.getEmpId());
            else if ("8".equals(choice)) payrollService.viewPayrollForEmployee(user.getEmpId());
            else if ("9".equals(choice)) holidayservice.viewHolidays();
            else if ("10".equals(choice)) addGoal(user);
            else if ("11".equals(choice)) updateGoal(user);
            else if ("12".equals(choice)) viewGoals(user);
            else if ("13".equals(choice)) { Session.logout(); exit=true; }
            else System.out.println("❌ Invalid choice!");
        }
    }
    //------------add goals----------------
    private void addGoal(Employee user) {
        try {
            Goal goal = new Goal();

            System.out.print("Goal Description: ");
            goal.setDescription(sc.nextLine());

            System.out.print("Deadline (yyyy-MM-dd): ");
            goal.setDeadline(simpleDateFormat.parse(sc.nextLine()));

            System.out.print("Priority (HIGH / MEDIUM / LOW): ");
            goal.setPriority(sc.nextLine().toUpperCase());

            System.out.print("Progress Percentage (0-100): ");
            goal.setProgressPercentage(Integer.parseInt(sc.nextLine()));

            goal.setStatus("PENDING");
            goal.setempId(user.getEmpId());   // 🔥 FIX

            goalservice.addGoal(user.getEmpId(), goal);
            System.out.println("✅ Goal added successfully");

        } catch (Exception e) {
            System.out.println("❌ Invalid input");
        }
    }
    
    //-----------VIEW GOALS---------------------    
    private void viewGoals(Employee user) {
        try {
            List<Goal> goals = goalservice.viewGoals(user.getEmpId());

            if (goals.isEmpty()) {
                System.out.println("⚠️ No goals found");
                return;
            }

            System.out.println("\nID | EMP_ID | DESCRIPTION | DEADLINE | PRIORITY | PROGRESS | STATUS");

            for (Goal g : goals) {
                System.out.println(
                    g.getGoalId() + " | " +
                    g.getempId() + " | " +
                    g.getDescription() + " | " +
                    g.getDeadline() + " | " +
                    g.getPriority() + " | " +
                    g.getProgressPercentage() + "% | " +
                    g.getStatus()
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Invalid input");
        }
    }
    
    //------------------update goal-----------------    
    private void updateGoal(Employee user) {
        try {
            Goal goal = new Goal();

            System.out.print("Enter Goal ID: ");
            goal.setGoalId(Integer.parseInt(sc.nextLine()));

            System.out.print("Description: ");
            goal.setDescription(sc.nextLine());

            System.out.print("Deadline (yyyy-MM-dd): ");
            goal.setDeadline(simpleDateFormat.parse(sc.nextLine()));

            System.out.print("Priority (HIGH / MEDIUM / LOW): ");
            goal.setPriority(sc.nextLine().toUpperCase());

            System.out.print("Progress Percentage (0-100): ");
            goal.setProgressPercentage(Integer.parseInt(sc.nextLine()));

            System.out.print("Status: ");
            goal.setStatus(sc.nextLine());

            goal.setempId(user.getEmpId()); // 🔥 FIX

            goalservice.updateGoal(user.getEmpId(), goal);
            System.out.println("✅ Goal updated successfully");

        } catch (Exception e) {
            e.printStackTrace(); // 🔥 FIX
        }
    }
    // ---------------- APPLY LEAVE ----------------
    private void applyLeave(Employee user) {
        try {
            LeaveRequest lr = new LeaveRequest();
            lr.setEmpId(user.getEmpId());

            System.out.print("Leave Type(1-sick/2-causal/3-paidLeave) ID: ");
            lr.setLeaveTypeId(Integer.parseInt(sc.nextLine()));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            System.out.print("From Date (yyyy-MM-dd): ");
            Date fromDate = simpleDateFormat.parse(sc.nextLine());

            System.out.print("To Date (yyyy-MM-dd): ");
            Date toDate = simpleDateFormat.parse(sc.nextLine());

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
            System.out.println("❌ Invalid input / date format");
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

            System.out.println("\nREQ_ID | TYPE_ID | FROM | TO | STATUS | REASON");

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
            System.out.println("❌ Error fetching leaves");
        }
    }

    // ---------------- VIEW LEAVE BALANCE ----------------
    private void viewLeaveBalance(Employee user) {
        try {
            List<LeaveBalance> balances =
                    leaveBalanceService.viewMyLeaveBalance(user.getEmpId());

            if (balances.isEmpty()) {
                System.out.println(" No leave balance found.");
                return;
            }

            System.out.println("\nTYPE_ID | AVAILABLE_DAYS");

            for (LeaveBalance lb : balances) {
                System.out.println(
                    lb.getLeaveTypeId() + " | " +
                    lb.getAvailableDays()
                );
            }

        } catch (Exception e) {
            System.out.println("❌ Error fetching leave balance");
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

    // ---------------- PERFORMANCE REVIEW ----------------
    private void viewPerformanceReviews(Employee user) {

        try {
            List<PerformanceReview> reviews =
                    reviewService.viewPerformanceReviews(user.getEmpId());

            if (reviews.isEmpty()) {
                System.out.println("⚠️ No performance reviews found.");
                return;
            }

            System.out.println("\nID | YEAR | RATING | MANAGER FEEDBACK");

            for (PerformanceReview pr : reviews) {
                System.out.println(
                    pr.getReviewId() + " | " +
                    pr.getReviewYear() + " | " +
                    pr.getRating() + " | " +
                    pr.getManagerFeedback()
                );
            }

        } catch (InvalidInputException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected error");
        }
    }

    // ---------------- VIEW NOTIFICATIONS ----------------
    private void viewNotifications(Employee user) {
        try {
            List<Notification> notifications =
                    notificationService.viewNotifications(user.getEmpId());

            if (notifications.isEmpty()) {
                System.out.println(" No notifications available.");
                return;
            }

            System.out.println("\n--- NOTIFICATIONS ---");

            for (Notification n : notifications) {
                System.out.println(
                        n.getCreatedAt() + " : " + n.getMessage()
                );
            }

        } catch (Exception e) {
            System.out.println("No notifications found");
        }
    }
    
    
}
