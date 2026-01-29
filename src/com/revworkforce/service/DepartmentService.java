package com.revworkforce.service;



import com.revworkforce.dao.DepartmentDAO;
import com.revworkforce.model.Department;

import java.util.List;
import java.util.Scanner;

public class DepartmentService {
    private static DepartmentDAO deptDAO = new DepartmentDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void manageDepartments() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Department Management ---");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View All Departments");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: addDepartment(); break;
                case 2: updateDepartment(); break;
                case 3: deleteDepartment(); break;
                case 4: viewDepartments(); break;
                case 5: back = true; break;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private static void addDepartment() {
        System.out.print("Department Name: "); String name = sc.nextLine();
        System.out.print("Location: "); String loc = sc.nextLine();
        Department dept = new Department(0, name, loc);
        if (deptDAO.addDepartment(dept)) System.out.println("✅ Department added successfully!");
        else System.out.println("❌ Failed to add department.");
    }

    private static void updateDepartment() {
        System.out.print("Department ID to update: "); int id = sc.nextInt(); sc.nextLine();
        Department dept = deptDAO.getDepartmentById(id);
        if (dept == null) { System.out.println("Department not found."); return; }
        System.out.print("New Name (" + dept.getDeptName() + "): "); String name = sc.nextLine();
        System.out.print("New Location (" + dept.getLocation() + "): "); String loc = sc.nextLine();
        dept.setDeptName(name); dept.setLocation(loc);
        if (deptDAO.updateDepartment(dept)) System.out.println("✅ Department updated successfully!");
        else System.out.println("❌ Failed to update department.");
    }

    private static void deleteDepartment() {
        System.out.print("Department ID to delete: "); int id = sc.nextInt();
        if (deptDAO.deleteDepartment(id)) System.out.println("✅ Department deleted successfully!");
        else System.out.println("❌ Failed to delete department.");
    }

    private static void viewDepartments() {
        List<Department> list = deptDAO.getAllDepartments();
        System.out.println("\nID\tName\tLocation");
        for (Department d : list) {
            System.out.println(d.getDeptId() + "\t" + d.getDeptName() + "\t" + d.getLocation());
        }
    }
}

