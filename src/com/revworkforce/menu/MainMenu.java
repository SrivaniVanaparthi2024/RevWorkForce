package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.model.Employee;

public class MainMenu {
    public void showMenu() {
        Employee user = Session.getCurrentUser();
        if (user == null) { 
            System.out.println("❌ No active session!"); 
            return; 
        }

        String role = user.getRole();
        boolean exit = false;

        while (!exit) {
            if ("ADMIN".equals(role)) {
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.showMenu();
                exit = true;

            } else if ("MANAGER".equals(role)) {
                ManagerMenu managerMenu = new ManagerMenu();
                managerMenu.showMenu();
                exit = true;

            } else if ("EMPLOYEE".equals(role)) {
                EmployeeMenu employeeMenu = new EmployeeMenu();
                employeeMenu.showMenu();
                exit = true;

            } else {
                System.out.println("❌ Unknown role!");
                exit = true;
            }
        }
    }
}
