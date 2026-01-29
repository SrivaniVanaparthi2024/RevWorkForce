package com.revworkforce.dao;

import com.revworkforce.model.Designation;
import com.revworkforce.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DesignationDAO {
    private Connection conn;

    public DesignationDAO() {
        conn = DBUtil.getConnection();
    }

    public List<Designation> getAllDesignations() {
        List<Designation> list = new ArrayList<Designation>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM designation";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Designation d = new Designation(
                        rs.getInt("designation_id"),
                        rs.getString("designation_name")
                );
                list.add(d);
            }
        } catch(SQLException e) {
            System.out.println("Error fetching designations: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(stmt != null) stmt.close(); } catch(Exception e) {}
        }
        return list;
    }

    public Designation getDesignationById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Designation d = null;
        try {
            String sql = "SELECT * FROM designation WHERE designation_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if(rs.next()) {
                d = new Designation(
                        rs.getInt("designation_id"),
                        rs.getString("designation_name")
                );
            }
        } catch(SQLException e) {
            System.out.println("Error fetching designation: " + e.getMessage());
        } finally {
            try { if(rs != null) rs.close(); } catch(Exception e) {}
            try { if(ps != null) ps.close(); } catch(Exception e) {}
        }
        return d;
    }
}
