package com.revworkforce.dao;

import com.revworkforce.model.PerformanceReview;
import com.revworkforce.util.DBUtil;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewDAO {

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
}
