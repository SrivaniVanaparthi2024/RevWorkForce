package com.revworkforce.service;

import com.revworkforce.dao.DepartmentDAO;
import com.revworkforce.model.Department;
import java.util.List;
import java.util.Scanner;

public class DepartmentService {
    private DepartmentDAO deptDAO = new DepartmentDAO();
    private Scanner sc = new Scanner(System.in);

    public void manageDepartments() {
        boolean back = false;
        while(!back) {
            System.out.println("\n--- Department Management ---");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View Departments");
            System.out.println("5. Back");
            System.out.print("Enter choice: "); 
            int choice = Integer.parseInt(sc.nextLine());

            if(choice == 1) addDepartment();
            else if(choice == 2) updateDepartment();
            else if(choice == 3) deleteDepartment();
            else if(choice == 4) viewDepartments();
            else if(choice == 5) back = true;
            else System.out.println("❌ Invalid choice");
        }
    }

    private void addDepartment() {
        System.out.print("Department Name: ");
        String name = sc.nextLine();

        System.out.print("Status (ACTIVE/INACTIVE): ");
        String status = sc.nextLine();

        Department dept = new Department(0, name, status);
        if (deptDAO.addDepartment(dept))
            System.out.println("✅ Added successfully");
        else
            System.out.println("❌ Failed to add");
    }

    private void updateDepartment() {
        System.out.print("Department ID to update: ");
        int id = Integer.parseInt(sc.nextLine());

        Department dept = deptDAO.getDepartmentById(id);
        if (dept == null) {
            System.out.println("❌ Not found");
            return;
        }

        System.out.print("New Name (" + dept.getDeptName() + "): ");
        String name = sc.nextLine();

        System.out.print("New Status (" + dept.getStatus() + "): ");
        String status = sc.nextLine();

        if (!name.isEmpty()) dept.setDeptName(name);
        if (!status.isEmpty()) dept.setStatus(status);

        if (deptDAO.updateDepartment(dept))
            System.out.println("✅ Updated successfully");
        else
            System.out.println("❌ Failed to update");
    }

    private void deleteDepartment() {
        System.out.print("Department ID to delete: "); int id = Integer.parseInt(sc.nextLine());
        if(deptDAO.deleteDepartment(id)) System.out.println("✅ Deleted successfully");
        else System.out.println("❌ Failed to delete");
    }

    private void viewDepartments() {
        List<Department> list = deptDAO.getAllDepartments();
        System.out.println("\nID\tName\tStatus");
        for (Department d : list) {
            System.out.println(
                d.getDeptId() + "\t" +
                d.getDeptName() + "\t" +
                d.getStatus()
            );
        }
    }
}
