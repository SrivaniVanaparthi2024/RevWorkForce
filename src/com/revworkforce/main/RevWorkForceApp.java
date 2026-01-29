package com.revworkforce.main;

import com.revworkforce.auth.AuthService;
import com.revworkforce.auth.Session;
import com.revworkforce.menu.MainMenu;
import com.revworkforce.util.DBUtil;

import java.sql.Connection;
import java.util.Scanner;

public class RevWorkForceApp {

    public static void main(String[] args) {

        Connection conn = DBUtil.getConnection();
        if (conn == null) {
            System.out.println("❌ Cannot connect to DB. Exiting...");
            return;
        }

        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        System.out.println("==== Welcome to RevWorkForce System ====");

        while (true) {

            // 🔐 Login loop
            while (!Session.isLoggedIn()) {
                boolean success = authService.login();
                if (!success) {
                    System.out.println("🔁 Please try again.\n");
                }
            }

            // Logged in user
            System.out.println(
                "Welcome " + Session.getCurrentUser().getEmpName()
            );

            // 📋 Show menu
            MainMenu mainMenu = new MainMenu();
            mainMenu.showMenu();

            // 🚪 Exit?
            System.out.print("\nDo you want to exit system? (Y/N): ");
            if (sc.nextLine().equalsIgnoreCase("Y")) {
                System.out.println(" Goodbye!");
                break;
            }

            // 🔓 Logout
            authService.logout();
        }

        sc.close();
    }
}
