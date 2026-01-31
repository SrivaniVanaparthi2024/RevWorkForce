package com.revworkforce.dao;

import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Goal;
import com.revworkforce.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoalDAO {

    // -------- ADD GOAL --------
    public void addGoal(Goal goal) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "INSERT INTO goals (goal_id, emp_id, description, deadline, priority, progress_percentage, status) " +
            "VALUES (goals_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, goal.getempId());
            ps.setString(2, goal.getDescription());
            ps.setDate(3, new java.sql.Date(goal.getDeadline().getTime()));
            ps.setString(4, goal.getPriority());
            ps.setInt(5, goal.getProgressPercentage());
            ps.setString(6, goal.getStatus());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new DatabaseException("Error adding goal");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    // -------- VIEW GOALS BY EMP --------
    public List<Goal> viewGoalsByEmp(int empId) throws DatabaseException {
        List<Goal> goals = new ArrayList<Goal>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
            "SELECT goal_id, emp_id, description, deadline, priority, progress_percentage, status " +
            "FROM goals WHERE emp_id = ? ORDER BY goal_id";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, empId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Goal g = new Goal();
                g.setGoalId(rs.getInt("goal_id"));
                g.setempId(rs.getInt("emp_id"));
                g.setDescription(rs.getString("description"));
                g.setDeadline(rs.getDate("deadline"));
                g.setPriority(rs.getString("priority"));
                g.setProgressPercentage(rs.getInt("progress_percentage"));
                g.setStatus(rs.getString("status"));

                goals.add(g);
            }

        } catch (Exception e) {
            throw new DatabaseException("Error fetching goals");
        } finally {
        	 try { if (rs != null) rs.close(); } catch (Exception e) {}
             try { if (ps != null) ps.close(); } catch (Exception e) {}
             try { if (con != null) con.close(); } catch (Exception e) {}
        }

        return goals;
    }

    // -------- UPDATE GOAL (EMP VALIDATION INCLUDED) --------
    public int updateGoal(Goal goal) throws DatabaseException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql =
            "UPDATE goals SET description=?, deadline=?, priority=?, progress_percentage=?, status=? " +
            "WHERE goal_id=? AND emp_id=?";

        try {
            con = DBUtil.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, goal.getDescription());
            ps.setDate(2, new java.sql.Date(goal.getDeadline().getTime()));
            ps.setString(3, goal.getPriority());
            ps.setInt(4, goal.getProgressPercentage());
            ps.setString(5, goal.getStatus());
            ps.setInt(6, goal.getGoalId());
            ps.setInt(7, goal.getempId());

            return ps.executeUpdate(); // returns 0 if not owned by emp

        } catch (Exception e) {
            throw new DatabaseException("Error updating goal");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}