package com.revworkforce.dao;

import com.revworkforce.model.Employee;
import com.revworkforce.util.DBUtil;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ---------------- FETCH ALL EMPLOYEES ----------------
    public List<Employee> getAllEmployees() throws DatabaseException, EmployeeNotFoundException {
        List<Employee> employees = new ArrayList<Employee>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT emp_id, name, email, role, dept_id, designation_id, manager_id, status FROM employee";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Integer managerId = rs.getInt("manager_id");
                if (rs.wasNull()) managerId = null;

                employees.add(new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("dept_id"),
                        rs.getInt("designation_id"),
                        rs.getString("role"),
                        rs.getString("status"),
                        managerId
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching employees", e);
        } finally {
            closeResources(rs, ps);
        }

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found");
        }

        return employees;
    }

    // ---------------- FETCH EMPLOYEE BY ID ----------------
    public Employee getEmployeeById(int empId) throws DatabaseException, EmployeeNotFoundException {
        Employee emp = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE emp_id=?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            if (rs.next()) {
                Integer managerId = rs.getInt("manager_id");
                if (rs.wasNull()) managerId = null;

                emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("dob"),
                        rs.getDate("joining_date"),
                        rs.getString("emergency_contact_name"),
                        rs.getString("emergency_contact_phone"),
                        rs.getString("role"),
                        rs.getInt("dept_id"),
                        rs.getInt("designation_id"),
                        managerId,
                        rs.getString("status")
                );
            } else {
                throw new EmployeeNotFoundException("Employee with ID " + empId + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching employee", e);
        } finally {
            closeResources(rs, ps);
        }

        return emp;
    }

    // ---------------- ADD EMPLOYEE ----------------
    public void addEmployee(Employee emp) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO employee " +
                "(emp_id, name, email, password, phone, address, dob, joining_date, " +
                "emergency_contact_name, emergency_contact_phone, role, dept_id, " +
                "designation_id, manager_id, status) " +
                "VALUES (emp_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, emp.getEmpName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPassword());
            ps.setString(4, emp.getPhone());
            ps.setString(5, emp.getAddress());
            ps.setDate(6, new java.sql.Date(emp.getDob().getTime()));
            ps.setDate(7, new java.sql.Date(emp.getJoiningDate().getTime()));
            ps.setString(8, emp.getEmergencyContactName());
            ps.setString(9, emp.getEmergencyContactPhone());
            ps.setString(10, emp.getRole());
            ps.setInt(11, emp.getDeptId());
            ps.setInt(12, emp.getDesignationId());

            if (emp.getManagerId() != null) {
                ps.setInt(13, emp.getManagerId());
            } else {
                ps.setNull(13, java.sql.Types.INTEGER);
            }

            ps.setString(14, emp.getStatus());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error adding employee", e);
        } finally {
            closeResources(null, ps);
        }
    }

    // ---------------- UPDATE EMPLOYEE ----------------
    public void updateEmployee(Employee emp) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE employee SET name=?, email=?, phone=?, address=?, " +
                "emergency_contact_name=?, emergency_contact_phone=?, role=?, " +
                "dept_id=?, designation_id=?, manager_id=?, status=? WHERE emp_id=?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, emp.getEmpName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getPhone());
            ps.setString(4, emp.getAddress());
            ps.setString(5, emp.getEmergencyContactName());
            ps.setString(6, emp.getEmergencyContactPhone());
            ps.setString(7, emp.getRole());
            ps.setInt(8, emp.getDeptId());
            ps.setInt(9, emp.getDesignationId());

            if (emp.getManagerId() != null) {
                ps.setInt(10, emp.getManagerId());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            ps.setString(11, emp.getStatus());
            ps.setInt(12, emp.getEmpId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating employee", e);
        } finally {
            closeResources(null, ps);
        }
    }

    // ---------------- SEARCH EMPLOYEES ----------------
    public List<Employee> searchEmployees(String keyword) throws DatabaseException, EmployeeNotFoundException {
        List<Employee> employees = new ArrayList<Employee>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT emp_id, name, email, role, dept_id, designation_id, manager_id, status " +
                     "FROM employee " +
                     "WHERE LOWER(name) LIKE ? OR LOWER(email) LIKE ?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            String value = "%" + keyword.toLowerCase() + "%";
            ps.setString(1, value);
            ps.setString(2, value);

            rs = ps.executeQuery();
            while (rs.next()) {
                Integer managerId = rs.getInt("manager_id");
                if (rs.wasNull()) managerId = null;

                employees.add(new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("dept_id"),
                        rs.getInt("designation_id"),
                        rs.getString("role"),
                        rs.getString("status"),
                        managerId
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error searching employees", e);
        } finally {
            closeResources(rs, ps);
        }

        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found for: " + keyword);
        }

        return employees;
    }

    // ---------------- CHANGE STATUS ----------------
    public void changeEmployeeStatus(int empId, String status) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE employee SET status=? WHERE emp_id=?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, empId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error updating employee status", e);
        } finally {
            closeResources(null, ps);
        }
    }
    
    public List<Employee> findByManager(int managerId) throws DatabaseException {

        List<Employee> employees = new ArrayList<Employee>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM employee WHERE manager_id = ?";

        try {
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, managerId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Employee emp = new Employee();
                emp.setEmpId(rs.getInt("emp_id"));
                emp.setEmpName(rs.getString("name"));
                emp.setEmail(rs.getString("email"));
                emp.setRole(rs.getString("role"));
                emp.setManagerId(rs.getInt("manager_id"));
                emp.setStatus(rs.getString("status"));
                employees.add(emp);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error fetching employees under manager", e);

        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return employees;
    }



    // ---------------- AUTHENTICATE EMPLOYEE ----------------
    public Employee getEmployeeByEmailAndPassword(String email, String password) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Employee emp = null;

        String sql = "SELECT * FROM employee WHERE email=? AND password=? AND status='ACTIVE'";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            if (rs.next()) {
                Integer managerId = rs.getInt("manager_id");
                if (rs.wasNull()) managerId = null;

                emp = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("dob"),
                        rs.getDate("joining_date"),
                        rs.getString("emergency_contact_name"),
                        rs.getString("emergency_contact_phone"),
                        rs.getString("role"),
                        rs.getInt("dept_id"),
                        rs.getInt("designation_id"),
                        managerId,
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error during authentication", e);
        } finally {
            closeResources(rs, ps);
        }

        return emp;
    }

    // ---------------- CLOSE RESOURCES ----------------
    private void closeResources(ResultSet rs, PreparedStatement ps) {
        try { if (rs != null) rs.close(); } catch (Exception e) {}
        try { if (ps != null) ps.close(); } catch (Exception e) {}
    }
}
