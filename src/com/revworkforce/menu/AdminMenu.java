package com.revworkforce.menu;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private Scanner sc = new Scanner(System.in);
    private EmployeeService employeeService = new EmployeeService();

    public void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n🔐 ADMIN MENU");
            System.out.println("1. Manage Employees");
            System.out.println("2. Manage Departments");
            System.out.println("3. Manage Leaves");
            System.out.println("4. Manage Holidays");
            System.out.println("5. Manage Announcements");
            System.out.println("6. Logout");

            System.out.print("Enter choice: ");
            String option = sc.nextLine();

            if ("1".equals(option)) manageEmployees();
            else if ("2".equals(option)) System.out.println("📌 Department Module Coming Soon...");
            else if ("3".equals(option)) System.out.println("📌 Leave Module Coming Soon...");
            else if ("4".equals(option)) System.out.println("📌 Holiday Module Coming Soon...");
            else if ("5".equals(option)) System.out.println("📌 Announcement Module Coming Soon...");
            else if ("6".equals(option)) { 
                System.out.println("👋 Logging out...");
                exit = true; 
            }
            else System.out.println("❌ Invalid choice! Please select 1-6.");
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
            String choice = sc.nextLine();

            if ("1".equals(choice)) {
                viewAllEmployees();
            } else if ("2".equals(choice)) {
                viewEmployeeById();
            } else if ("3".equals(choice)) {
                System.out.println("📌 Add Employee Module Coming Soon...");
            } else if ("4".equals(choice)) {
                System.out.println("📌 Update Employee Module Coming Soon...");
            } else if ("5".equals(choice)) {
                System.out.println("📌 Deactivate/Reactivate Module Coming Soon...");
            } else if ("6".equals(choice)) {
                System.out.println("📌 Assign/Change Manager Module Coming Soon...");
            } else if ("7".equals(choice)) {
                System.out.println("📌 Search Employee Module Coming Soon...");
            } else if ("8".equals(choice)) {
                back = true;
            } else {
                System.out.println("❌ Invalid choice! Please select 1-8.");
            }

        }
    }

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
            System.out.print("Role (ADMIN/MANAGER/EMPLOYEE): "); emp.setRole(sc.nextLine().toUpperCase());
            System.out.print("Dept ID: "); emp.setDeptId(Integer.parseInt(sc.nextLine()));
            System.out.print("Designation ID: "); emp.setDesignationId(Integer.parseInt(sc.nextLine()));
            emp.setStatus("ACTIVE");

            employeeService.addEmployee(emp);
            System.out.println("✅ Employee added successfully!");
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter valid numeric values for IDs.");
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

            employeeService.updateEmployee(emp);
            System.out.println("✅ Employee updated successfully!");
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Enter valid numeric values for IDs.");
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
            emp.setManagerId(managerId);

            employeeService.updateEmployee(emp);
            System.out.println("✅ Manager assigned successfully!");
        } catch (EmployeeNotFoundException e) {
            System.out.println("⚠️ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
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
        System.out.println("Status   : " + emp.getStatus());
    }
}
