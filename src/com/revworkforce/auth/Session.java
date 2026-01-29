package com.revworkforce.auth;

import com.revworkforce.model.Employee;

public class Session {

    private static Employee currentUser;

    private Session() {}

    public static void setCurrentUser(Employee user) {
        currentUser = user;
    }

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static int getEmpId() {
        return currentUser != null ? currentUser.getEmpId() : 0;
    }

    public static String getRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }
    
    public static void logout() {
        currentUser = null;
        System.out.println("👋 Logged out successfully");
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}
