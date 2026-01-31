package com.revworkforce.service;

import com.revworkforce.dao.DepartmentDAO;
import com.revworkforce.model.Department;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepartmentService {
    
    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    private DepartmentDAO deptDAO = new DepartmentDAO();
    private Scanner sc = new Scanner(System.in);

    public void manageDepartments() {
        logger.info("Department Management menu opened");

        boolean back = false;
        while(!back) {
            System.out.println("\n--- Department Management ---");
            System.out.println("1. Add Department");
            System.out.println("2. Update Department");
            System.out.println("3. Delete Department");
            System.out.println("4. View Departments");
            System.out.println("5. Back");
            System.out.print("Enter choice: "); 
            
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input. Enter a number.");
                logger.warn("Invalid menu input in Department Management: {}", e.getMessage());
                continue;
            }

            if(choice == 1) {
                logger.info("Option selected: Add Department");
                addDepartment();
            } else if(choice == 2) {
                logger.info("Option selected: Update Department");
                updateDepartment();
            } else if(choice == 3) {
                logger.info("Option selected: Delete Department");
                deleteDepartment();
            } else if(choice == 4) {
                logger.info("Option selected: View Departments");
                viewDepartments();
            } else if(choice == 5) {
                logger.info("Option selected: Back to Main Menu");
                back = true;
            } else {
                System.out.println("❌ Invalid choice");
                logger.warn("Invalid menu choice in Department Management: {}", choice);
            }
        }

        logger.info("Department Management menu closed");
    }

    private void addDepartment() {
        try {
            System.out.print("Department Name: ");
            String name = sc.nextLine();

            System.out.print("Status (ACTIVE/INACTIVE): ");
            String status = sc.nextLine().toUpperCase();

            if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
                System.out.println("❌ Invalid status. Must be ACTIVE or INACTIVE.");
                logger.warn("Invalid status input for department: {}", status);
                return;
            }

            Department dept = new Department(0, name, status);
            if (deptDAO.addDepartment(dept)) {
                System.out.println("✅ Added successfully");
                logger.info("Department added: {} [{}]", name, status);
            } else {
                System.out.println("❌ Failed to add");
                logger.error("Failed to add department: {} [{}]", name, status);
            }
        } catch (Exception e) {
            System.out.println("❌ Error occurred while adding department.");
            logger.error("Exception in addDepartment(): ", e);
        }
    }

    private void updateDepartment() {
        try {
            System.out.print("Department ID to update: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            Department dept = deptDAO.getDepartmentById(id);
            if (dept == null) {
                System.out.println("❌ Not found");
                logger.warn("Department not found for update, ID={}", id);
                return;
            }

            System.out.print("New Name (" + dept.getDeptName() + "): ");
            String name = sc.nextLine();

            System.out.print("New Status (" + dept.getStatus() + "): ");
            String status = sc.nextLine().toUpperCase();

            if (!name.isEmpty()) dept.setDeptName(name);
            if (!status.isEmpty()) {
                if (!status.equals("ACTIVE") && !status.equals("INACTIVE")) {
                    System.out.println("❌ Invalid status. Must be ACTIVE or INACTIVE.");
                    logger.warn("Invalid status input for update: {}", status);
                    return;
                }
                dept.setStatus(status);
            }

            if (deptDAO.updateDepartment(dept)) {
                System.out.println("✅ Updated successfully");
                logger.info("Department updated: ID={} Name={} Status={}", id, dept.getDeptName(), dept.getStatus());
            } else {
                System.out.println("❌ Failed to update");
                logger.error("Failed to update department: ID={}", id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Department ID must be a number.");
            logger.warn("Invalid Department ID input in updateDepartment(): {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error occurred while updating department.");
            logger.error("Exception in updateDepartment(): ", e);
        }
    }

    private void deleteDepartment() {
        try {
            System.out.print("Department ID to delete: "); 
            int id = Integer.parseInt(sc.nextLine().trim());
            if(deptDAO.deleteDepartment(id)) {
                System.out.println("✅ Deleted successfully");
                logger.info("Department deleted, ID={}", id);
            } else {
                System.out.println("❌ Failed to delete");
                logger.error("Failed to delete department, ID={}", id);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Department ID must be a number.");
            logger.warn("Invalid Department ID input in deleteDepartment(): {}", e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error occurred while deleting department.");
            logger.error("Exception in deleteDepartment(): ", e);
        }
    }

    private void viewDepartments() {
        try {
            List<Department> list = deptDAO.getAllDepartments();
            System.out.println("\nID\tName\tStatus");
            for (Department d : list) {
                System.out.println(d.getDeptId() + "\t" + d.getDeptName() + "\t" + d.getStatus());
            }
            logger.info("Viewed all departments, count={}", list.size());
        } catch (Exception e) {
            System.out.println("❌ Error fetching department list.");
            logger.error("Exception in viewDepartments(): ", e);
        }
    }
}