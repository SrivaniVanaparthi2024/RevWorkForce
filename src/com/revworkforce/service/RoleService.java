package com.revworkforce.service;


import com.revworkforce.dao.RoleDAO;
import com.revworkforce.model.Role;

import java.util.List;
import java.util.Scanner;

public class RoleService {
    private static RoleDAO roleDAO = new RoleDAO();
    private static Scanner sc = new Scanner(System.in);

    public static void manageRoles() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Role Management ---");
            System.out.println("1. Add Role");
            System.out.println("2. Update Role");
            System.out.println("3. Delete Role");
            System.out.println("4. View All Roles");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt(); sc.nextLine();

            switch (choice) {
                case 1: addRole(); break;
                case 2: updateRole(); break;
                case 3: deleteRole(); break;
                case 4: viewRoles(); break;
                case 5: back = true; break;
                default: System.out.println("Invalid choice."); break;
            }
        }
    }

    private static void addRole() {
        System.out.print("Role Name: "); String name = sc.nextLine();
        System.out.print("Description: "); String desc = sc.nextLine();
        Role role = new Role(0, name, desc);
        if (roleDAO.addRole(role)) System.out.println("✅ Role added successfully!");
        else System.out.println("❌ Failed to add role.");
    }

    private static void updateRole() {
        System.out.print("Role ID to update: "); int id = sc.nextInt(); sc.nextLine();
        Role role = roleDAO.getRoleById(id);
        if (role == null) { System.out.println("Role not found."); return; }
        System.out.print("New Name (" + role.getRoleName() + "): "); String name = sc.nextLine();
        System.out.print("New Description (" + role.getDescription() + "): "); String desc = sc.nextLine();
        role.setRoleName(name); role.setDescription(desc);
        if (roleDAO.updateRole(role)) System.out.println("✅ Role updated successfully!");
        else System.out.println("❌ Failed to update role.");
    }

    private static void deleteRole() {
        System.out.print("Role ID to delete: "); int id = sc.nextInt();
        if (roleDAO.deleteRole(id)) System.out.println("✅ Role deleted successfully!");
        else System.out.println("❌ Failed to delete role.");
    }

    private static void viewRoles() {
        List<Role> list = roleDAO.getAllRoles();
        System.out.println("\nID\tRole Name\tDescription");
        for (Role r : list) {
            System.out.println(r.getRoleId() + "\t" + r.getRoleName() + "\t" + r.getDescription());
        }
    }
}

