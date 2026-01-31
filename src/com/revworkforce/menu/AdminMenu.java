package com.revworkforce.menu;

import com.revworkforce.model.Employee;
import com.revworkforce.service.AttendanceService;
import com.revworkforce.service.DepartmentService;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.service.HolidayService;
import com.revworkforce.service.PayrollService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;
import java.util.Scanner;
import java.sql.Date;

public class AdminMenu {

	private Scanner sc = new Scanner(System.in);
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();
    private AttendanceService attendanceService = new AttendanceService();
    private PayrollService payrollService = new PayrollService();
    private HolidayService holidayService = new HolidayService();
    public void showMenu() throws InvalidInputException, DatabaseException {

        boolean exit = false;

        while (!exit) {

            System.out.println("\n ADMIN MENU");
            System.out.println("1. Manage Employees");
            System.out.println("2. Manage Departments");
            System.out.println("3. Manage Attendance");
            System.out.println("4. Manage Payroll");
            System.out.println("5. view Holidays");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            int option = sc.nextInt();
            sc.nextLine();
            switch (option) {

                case 1:
                    manageEmployees();
                    break;

                case 2:
                    departmentService.manageDepartments();
                    break;

                case 3:
                    attendanceService.manageAttendance();
                    break;

                case 4:
                    payrollService.managePayroll();
                    break;

                case 5:
                	holidayService.viewHolidays();
                	break;
                case 6:
                    System.out.println(" Logging out...");
                    exit = true;   // return control to main loop
                    break;

                default:
                    System.out.println("❌ Invalid choice! Please select 1-5.");
            }
        }
    }



    // ---------------- EMPLOYEE MANAGEMENT ----------------
    private void manageEmployees() {

        boolean back = false;

        while (!back) {

            System.out.println("\n=== ADMIN → EMPLOYEE MANAGEMENT ===");
            System.out.println("1. View All Employees");
            System.out.println("2. View Employee By ID");
            System.out.println("3. Add New Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Deactivate/Reactivate Employee");
            System.out.println("6. Assign/Change Manager");
            System.out.println("7. Search Employee");
            System.out.println("8. Back");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {

                case 1:
                    viewAllEmployees();
                    break;

                case 2:
                    viewEmployeeById();
                    break;

                case 3:
                    addEmployee();
                    break;

                case 4:
                    updateEmployee();
                    break;

                case 5:
                    toggleEmployeeStatus();
                    break;

                case 6:
                    assignManager();
                    break;

                case 7:
                    searchEmployee();
                    break;

                case 8:
                    back = true;
                    break;

                default:
                    System.out.println("❌ Invalid choice! Please select 1-8.");
            }
        }
    }



    // ---------------- EMPLOYEE FUNCTIONS ----------------
    private void viewAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllEmployees();
            System.out.println("\nEMP_ID | NAME | EMAIL | ROLE | DEPT_ID | DESIG_ID | STATUS");
            for (Employee emp : employees) {
                System.out.println(emp.getEmpId() + " | " +
                                   emp.getEmpName() + " | " +
                                   emp.getEmail() + " | " +
                                   emp.getRole() + " | " +
                                   emp.getDeptId() + " | " +
                                   emp.getDesignationId() + " | " +
                                   emp.getStatus());
            }
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }

    private void viewEmployeeById() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();
            Employee emp = employeeService.getEmployeeById(empId);
            printEmployeeDetails(emp);
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter a valid number.");
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }

    private void addEmployee() {
        try {
            Employee emp = new Employee();

            // ---------------- MANDATORY FIELDS ----------------
            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) { System.out.println("❌ Name is required."); return; }
            emp.setEmpName(name);

            System.out.print("Email (@rev.com): ");
            String email = sc.nextLine().trim().toLowerCase();
            if (!email.endsWith("@rev.com") || email.length() <= 8) {
                System.out.println("❌ Invalid email format."); return;
            }
            emp.setEmail(email);

            System.out.print("Password (min 6 chars): ");
            String pwd = sc.nextLine().trim();
            if (pwd.length() < 6) { System.out.println("❌ Password too short."); return; }
            emp.setPassword(pwd);

            System.out.print("Role (ADMIN/MANAGER/EMPLOYEE): ");
            String role = sc.nextLine().trim().toUpperCase();
            if (!role.equals("ADMIN") && !role.equals("MANAGER") && !role.equals("EMPLOYEE")) {
                System.out.println("❌ Invalid role."); return;
            }
            emp.setRole(role);

            System.out.print("Dept ID(HR-1/IT-2): ");
            int deptId = Integer.parseInt(sc.nextLine().trim());
            emp.setDeptId(deptId);

            System.out.print("Designation ID(Manager-1/Emp-2): ");
            int desigId = Integer.parseInt(sc.nextLine().trim());
            emp.setDesignationId(desigId);

            System.out.print("Phone (10 digits): ");
            String phone = sc.nextLine().trim();
            if (!phone.matches("\\d{10}")) { System.out.println("❌ Invalid phone number."); return; }
            emp.setPhone(phone);

            System.out.print("Address: ");
            emp.setAddress(sc.nextLine().trim());

            System.out.print("DOB (yyyy-MM-dd): ");
            try { emp.setDob(Date.valueOf(sc.nextLine().trim())); }
            catch (IllegalArgumentException e) { System.out.println("❌ Invalid DOB format."); return; }

            System.out.print("Joining Date (yyyy-MM-dd): ");
            try { emp.setJoiningDate(Date.valueOf(sc.nextLine().trim())); }
            catch (IllegalArgumentException e) { System.out.println("❌ Invalid joining date format."); return; }

            System.out.print("Emergency Contact Name: ");
            emp.setEmergencyContactName(sc.nextLine().trim());

            System.out.print("Emergency Contact Phone: ");
            String ePhone = sc.nextLine().trim();
            if (!ePhone.isEmpty() && !ePhone.matches("\\d{10}")) {
                System.out.println("❌ Invalid emergency contact phone."); return;
            }
            emp.setEmergencyContactPhone(ePhone);

            emp.setStatus("ACTIVE");

            employeeService.addEmployee(emp);
            System.out.println("✅ Employee added successfully!");

        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid numeric input for Dept or Designation ID.");
        }
    }

    private void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int empId = Integer.parseInt(sc.nextLine().trim());
            Employee emp = employeeService.getEmployeeById(empId);

            System.out.print("New Name (" + emp.getEmpName() + "): ");
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) emp.setEmpName(name);

            System.out.print("New Email (" + emp.getEmail() + "): ");
            String email = sc.nextLine().trim().toLowerCase();
            if (!email.isEmpty()) {
                if (!email.endsWith("@rev.com") || email.length() <= 8) {
                    System.out.println("❌ Invalid email format."); return;
                }
                emp.setEmail(email);
            }

            System.out.print("New Role (" + emp.getRole() + "): ");
            String role = sc.nextLine().trim().toUpperCase();
            if (!role.isEmpty()) {
                if (!role.equals("ADMIN") && !role.equals("MANAGER") && !role.equals("EMPLOYEE")) {
                    System.out.println("❌ Invalid role."); return;
                }
                emp.setRole(role);
            }

            System.out.print("New Dept ID (" + emp.getDeptId() + "): ");
            String dept = sc.nextLine().trim();
            if (!dept.isEmpty()) emp.setDeptId(Integer.parseInt(dept));

            System.out.print("New Designation ID (" + emp.getDesignationId() + "): ");
            String desig = sc.nextLine().trim();
            if (!desig.isEmpty()) emp.setDesignationId(Integer.parseInt(desig));

            System.out.print("New Phone (" + emp.getPhone() + "): ");
            String phone = sc.nextLine().trim();
            if (!phone.isEmpty() && !phone.matches("\\d{10}")) {
                System.out.println("❌ Invalid phone number."); return;
            }
            if (!phone.isEmpty()) emp.setPhone(phone);

            System.out.print("New Address (" + emp.getAddress() + "): ");
            String address = sc.nextLine().trim();
            if (!address.isEmpty()) emp.setAddress(address);

            System.out.print("New DOB (" + emp.getDob() + "): ");
            String dob = sc.nextLine().trim();
            if (!dob.isEmpty()) {
                try { emp.setDob(Date.valueOf(dob)); }
                catch (IllegalArgumentException e) { System.out.println("❌ Invalid DOB format."); return; }
            }

            System.out.print("New Joining Date (" + emp.getJoiningDate() + "): ");
            String joining = sc.nextLine().trim();
            if (!joining.isEmpty()) {
                try { emp.setJoiningDate(Date.valueOf(joining)); }
                catch (IllegalArgumentException e) { System.out.println("❌ Invalid joining date format."); return; }
            }

            System.out.print("New Emergency Contact Name (" + emp.getEmergencyContactName() + "): ");
            String eName = sc.nextLine().trim();
            if (!eName.isEmpty()) emp.setEmergencyContactName(eName);

            System.out.print("New Emergency Contact Phone (" + emp.getEmergencyContactPhone() + "): ");
            String ePhone = sc.nextLine().trim();
            if (!ePhone.isEmpty() && !ePhone.matches("\\d{10}")) {
                System.out.println("❌ Invalid emergency contact phone."); return;
            }
            if (!ePhone.isEmpty()) emp.setEmergencyContactPhone(ePhone);

            employeeService.updateEmployee(emp);
            System.out.println("✅ Employee updated successfully!");

        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid numeric input for IDs.");
        }
    }

    private void toggleEmployeeStatus() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();
            Employee emp = employeeService.getEmployeeById(empId);
            String newStatus = emp.getStatus().equalsIgnoreCase("ACTIVE") ? "INACTIVE" : "ACTIVE";
            emp.setStatus(newStatus);
            employeeService.updateEmployee(emp);
            System.out.println("✅ Employee status changed to " + newStatus);
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }

    private void assignManager() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();
            Employee emp = employeeService.getEmployeeById(empId);

            System.out.print("Enter Manager Employee ID: ");
            int managerId = Integer.parseInt(sc.nextLine());

            Employee manager = employeeService.getEmployeeById(managerId);
            if (!manager.getRole().equalsIgnoreCase("MANAGER") && !manager.getRole().equalsIgnoreCase("ADMIN")) {
                System.out.println("❌ Selected employee is not a valid manager.");
                return;
            }

            emp.setManagerId(managerId);
            employeeService.updateEmployee(emp);
            System.out.println("✅ Manager assigned successfully!");
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter valid numeric IDs.");
        }
    }

    private void searchEmployee() {
        System.out.print("Enter name, email, or role to search: ");
        String keyword = sc.nextLine();
        try {
            List<Employee> employees = employeeService.searchEmployees(keyword);
            if (employees.isEmpty()) System.out.println("⚠️ No matching employees found.");
            else for (Employee emp : employees) printEmployeeDetails(emp);
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
    }

    private void printEmployeeDetails(Employee emp) {
        System.out.println("\n--- EMPLOYEE DETAILS ---");
        System.out.println("ID       : " + emp.getEmpId());
        System.out.println("Name     : " + emp.getEmpName());
        System.out.println("Email    : " + emp.getEmail());
        System.out.println("Role     : " + emp.getRole());
        System.out.println("Dept ID  : " + emp.getDeptId());
        System.out.println("Desig ID : " + emp.getDesignationId());
        System.out.println("Manager  : " + emp.getManagerId());
        System.out.println("Phone    : " + emp.getPhone());
        System.out.println("Address  : " + emp.getAddress());
        System.out.println("DOB      : " + emp.getDob());
        System.out.println("Joining  : " + emp.getJoiningDate());
        System.out.println("Emergency Name  : " + emp.getEmergencyContactName());
        System.out.println("Emergency Phone : " + emp.getEmergencyContactPhone());
        System.out.println("Status   : " + emp.getStatus());
    }
}
