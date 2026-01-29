package com.revworkforce.menu;

import com.revworkforce.auth.Session;
import com.revworkforce.model.Employee;

import java.util.Scanner;

public class ManagerMenu {
    private Scanner sc = new Scanner(System.in);

    public void showMenu() {
        Employee user = Session.getCurrentUser();
        if (user == null) { System.out.println("❌ No active session!"); return; }

        boolean exit = false;
        while (!exit) {
            System.out.println("\n📋 MANAGER MENU");
            System.out.println("1. Approve Leave");
            System.out.println("2. View Team");
            System.out.println("3. Logout");

            System.out.print("Enter choice: ");
            String choice = sc.nextLine();

            if ("1".equals(choice)) System.out.println("📌 Leave Approval Module Coming Soon...");
            else if ("2".equals(choice)) System.out.println("📌 View Team Module Coming Soon...");
            else if ("3".equals(choice)) { System.out.println("👋 Logging out..."); exit=true; }
            else System.out.println("❌ Invalid choice! Please select 1-3.");
        }
    }
}
