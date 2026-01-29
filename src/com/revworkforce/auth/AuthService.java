package com.revworkforce.auth;

import com.revworkforce.model.Employee;
import com.revworkforce.service.EmployeeService;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;

import java.util.Scanner;

public class AuthService {

    private EmployeeService employeeService;
    private Scanner sc = new Scanner(System.in);

    public AuthService() {
        this.employeeService = new EmployeeService();
    }

    public boolean login() {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Email    : ");
        String email = sc.nextLine();
        System.out.print("Password : ");
        String password = sc.nextLine();

        try {
            Employee emp = employeeService.login(email, password);
            Session.setCurrentUser(emp);
            System.out.println("✅ Login successful!");
            return true;

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());

        } catch (EmployeeNotFoundException e) {
            System.out.println("❌ Invalid email or password!");

        } catch (DatabaseException e) {
            System.out.println("❌ DB Error: " + e.getMessage());
        }
        return false;
    }


    public void logout() {
        Session.logout();
    }
}
