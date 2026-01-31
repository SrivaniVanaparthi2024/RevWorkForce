// ----------------- MainMenu.java -----------------
package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Employee;

public class MainMenu {
    public void showMenu() throws DatabaseException, InvalidInputException {
        Employee user = Session.getCurrentUser();
        if (user == null) { 
            System.out.println("❌ No active session!"); 
            return; 
        }

        String role = user.getRole();
        boolean exit = false;

        while (!exit) {
            if ("ADMIN".equals(role)) { new AdminMenu().showMenu(); exit=true; }
            else if ("MANAGER".equals(role)) { new ManagerMenu().showMenu(); exit=true; }
            else if ("EMPLOYEE".equals(role)) { new EmployeeMenu().showMenu(); exit=true; }
            else { System.out.println("❌ Unknown role!"); exit=true; }
        }
    }
}
