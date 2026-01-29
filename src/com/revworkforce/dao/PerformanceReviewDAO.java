
package com.revworkforce.dao;

import com.revworkforce.model.PerformanceReview;
import com.revworkforce.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PerformanceReviewDAO {

    public List<PerformanceReview> getReviewsByEmployeeId(int empId) {
        List<PerformanceReview> reviews = new ArrayList<PerformanceReview>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBUtil.getConnection();

            String sql = "SELECT review_id, emp_id, review_period, rating, comments, reviewed_by, review_date " +
                         "FROM performance_review WHERE emp_id = ?";

            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {
                PerformanceReview pr = new PerformanceReview();
                pr.setReviewId(rs.getInt("review_id"));
                pr.setEmpId(rs.getInt("emp_id"));
                pr.setReviewPeriod(rs.getString("review_period"));
                pr.setRating(rs.getInt("rating"));
                pr.setComments(rs.getString("comments"));
                pr.setReviewedBy(rs.getInt("reviewed_by"));
                pr.setReviewDate(rs.getDate("review_date"));

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
