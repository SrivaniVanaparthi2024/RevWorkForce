package com.revworkforce.dao.impl;

import com.revworkforce.model.PerformanceReview;
import com.revworkforce.util.DBUtil;
import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewDAOImpl implements PerformanceReviewDAO {

    public List<PerformanceReview> getReviewsByEmployeeId(int empId) throws DatabaseException {

        List<PerformanceReview> reviews = new ArrayList<PerformanceReview>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();

            String sql =
                "SELECT review_id, emp_id, review_year, self_review, manager_feedback, rating " +
                "FROM performance_review " +
                "WHERE emp_id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {

                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setEmpId(rs.getInt("emp_id"));
                pr.setReviewYear(rs.getInt("review_year"));
                pr.setSelfReview(rs.getString("self_review"));
                pr.setManagerFeedback(rs.getString("manager_feedback"));
                pr.setRating(rs.getInt("rating"));

                reviews.add(pr);
            }

        } catch (Exception e) {
            throw new DatabaseException("Error fetching performance reviews", e);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return reviews;
    }
 // ---------- INSERT MANAGER REVIEW ----------
    public void addManagerReview(PerformanceReview pr) throws DatabaseException {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "INSERT INTO performance_review " +
            "(review_id, emp_id, review_year, self_review, manager_feedback, rating)"+
            		"VALUES (review_seq.NEXTVAL, ?, ?, NULL, ?, ?)";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, pr.getEmpId());
            ps.setInt(2, pr.getReviewYear());
            ps.setString(3, pr.getManagerFeedback());
            ps.setInt(4, pr.getRating());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DatabaseException("Error adding manager review", e);
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
