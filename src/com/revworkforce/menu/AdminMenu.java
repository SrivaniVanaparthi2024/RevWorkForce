package com.revworkforce.menu;

import com.revworkforce.model.Employee;
import com.revworkforce.service.AttendanceService;
import com.revworkforce.service.DepartmentService;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.service.PayrollService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Scanner;
import java.sql.Date;

public class AdminMenu {

	private Scanner sc = new Scanner(System.in);
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();
    private AttendanceService attendanceService = new AttendanceService();
    private PayrollService payrollService = new PayrollService();

    public void showMenu() {

        boolean exit = false;

        while (!exit) {

            System.out.println("\n ADMIN MENU");
            System.out.println("1. Manage Employees");
            System.out.println("2. Manage Departments");
            System.out.println("3. Manage Attendance");
            System.out.println("4. Manage Payroll");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");
            int option = sc.nextInt();

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
                    System.out.println("👋 Logging out...");
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
            int empId = Integer.parseInt(sc.nextLine());
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

            System.out.print("Name: "); emp.setEmpName(sc.nextLine());
            System.out.print("Email: "); emp.setEmail(sc.nextLine());
            System.out.print("Password: "); emp.setPassword(sc.nextLine());
            System.out.print("Role (ADMIN/MANAGER/EMPLOYEE): "); emp.setRole(sc.nextLine().toUpperCase());
            System.out.print("Dept ID: "); emp.setDeptId(Integer.parseInt(sc.nextLine()));
            System.out.print("Designation ID: "); emp.setDesignationId(Integer.parseInt(sc.nextLine()));
            System.out.print("Phone: "); emp.setPhone(sc.nextLine());
            System.out.print("Address: "); emp.setAddress(sc.nextLine());
            System.out.print("DOB (yyyy-mm-dd): "); emp.setDob(Date.valueOf(sc.nextLine()));
            System.out.print("Joining Date (yyyy-mm-dd): "); emp.setJoiningDate(Date.valueOf(sc.nextLine()));
            System.out.print("Emergency Contact Name: "); emp.setEmergencyContactName(sc.nextLine());
            System.out.print("Emergency Contact Phone: "); emp.setEmergencyContactPhone(sc.nextLine());
            emp.setStatus("ACTIVE");

            employeeService.addEmployee(emp);
            System.out.println("✅ Employee added successfully!");
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter valid numeric values for IDs.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid date format. Use yyyy-mm-dd.");
        }
    }

    private void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to update: ");
            int empId = Integer.parseInt(sc.nextLine());
            Employee emp = employeeService.getEmployeeById(empId);

            System.out.print("New Name (" + emp.getEmpName() + "): "); 
            String name = sc.nextLine(); if (!name.isEmpty()) emp.setEmpName(name);

            System.out.print("New Email (" + emp.getEmail() + "): "); 
            String email = sc.nextLine(); if (!email.isEmpty()) emp.setEmail(email);

            System.out.print("New Role (" + emp.getRole() + "): "); 
            String role = sc.nextLine(); if (!role.isEmpty()) emp.setRole(role.toUpperCase());

            System.out.print("New Dept ID (" + emp.getDeptId() + "): "); 
            String dept = sc.nextLine(); if (!dept.isEmpty()) emp.setDeptId(Integer.parseInt(dept));

            System.out.print("New Designation ID (" + emp.getDesignationId() + "): "); 
            String desig = sc.nextLine(); if (!desig.isEmpty()) emp.setDesignationId(Integer.parseInt(desig));

            System.out.print("New Phone (" + emp.getPhone() + "): ");
            String phone = sc.nextLine(); if (!phone.isEmpty()) emp.setPhone(phone);

            System.out.print("New Address (" + emp.getAddress() + "): ");
            String address = sc.nextLine(); if (!address.isEmpty()) emp.setAddress(address);

            System.out.print("New DOB (" + emp.getDob() + "): ");
            String dob = sc.nextLine(); if (!dob.isEmpty()) emp.setDob(Date.valueOf(dob));

            System.out.print("New Joining Date (" + emp.getJoiningDate() + "): ");
            String joining = sc.nextLine(); if (!joining.isEmpty()) emp.setJoiningDate(Date.valueOf(joining));

            System.out.print("New Emergency Contact Name (" + emp.getEmergencyContactName() + "): ");
            String eName = sc.nextLine(); if (!eName.isEmpty()) emp.setEmergencyContactName(eName);

            System.out.print("New Emergency Contact Phone (" + emp.getEmergencyContactPhone() + "): ");
            String ePhone = sc.nextLine(); if (!ePhone.isEmpty()) emp.setEmergencyContactPhone(ePhone);

            employeeService.updateEmployee(emp);
            System.out.println("✅ Employee updated successfully!");
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter valid numeric values for IDs.");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Invalid date format. Use yyyy-mm-dd.");
        }
    }

    private void toggleEmployeeStatus() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(sc.nextLine());
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
            int empId = Integer.parseInt(sc.nextLine());
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
