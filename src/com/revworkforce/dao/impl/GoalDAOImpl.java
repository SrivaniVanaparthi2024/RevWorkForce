package com.revworkforce.dao.impl;

import com.revworkforce.dao.GoalDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Goal;
import com.revworkforce.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GoalDAOImpl implements GoalDAO{

    // -------- ADD GOAL --------
    public void addGoal(Goal goal) throws DatabaseException {
        Connection connect = null;
        PreparedStatement preparedstatement = null;

        String sql =
            "INSERT INTO goal (goal_id, emp_id, description, deadline, priority, progress_percentage, status) " +
            "VALUES (goals_seq.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            connect = DBUtil.getConnection();
            
            preparedstatement = connect.prepareStatement(sql);

            preparedstatement.setInt(1, goal.getempId());
            preparedstatement.setString(2, goal.getDescription());
            preparedstatement.setDate(3, new java.sql.Date(goal.getDeadline().getTime()));
            preparedstatement.setString(4, goal.getPriority());
            preparedstatement.setInt(5, goal.getProgressPercentage());
            preparedstatement.setString(6, goal.getStatus());

            preparedstatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to add goal");
        } finally {
            try { if (preparedstatement != null) preparedstatement.close(); } catch (Exception e) {}
        }
    }

    // -------- VIEW GOALS BY EMP --------
    public List<Goal> viewGoalsByEmp(int empId) throws DatabaseException {

        List<Goal> goals = new ArrayList<Goal>();
        Connection connect = null;
        PreparedStatement preparedstatement = null;
        ResultSet resultset = null;

        String sql =
            "SELECT goal_id, emp_id, description, deadline, priority, progress_percentage, status " +
            "FROM goal WHERE emp_id = ? ORDER BY goal_id";

        try {
            connect = DBUtil.getConnection();
            preparedstatement = connect.prepareStatement(sql);
            preparedstatement.setInt(1, empId);

            resultset = preparedstatement.executeQuery();

            while (resultset.next()) {
                Goal g = new Goal();
                g.setGoalId(resultset.getInt("goal_id"));
                g.setempId(resultset.getInt("emp_id"));
                g.setDescription(resultset.getString("description"));
                g.setDeadline(resultset.getDate("deadline"));
                g.setPriority(resultset.getString("priority"));
                g.setProgressPercentage(resultset.getInt("progress_percentage"));
                g.setStatus(resultset.getString("status"));

                goals.add(g);
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            throw new DatabaseException("Error fetching goals");
        } finally {
            try { if (resultset != null) resultset.close(); } catch (Exception e) {}
            try { if (preparedstatement != null) preparedstatement.close(); } catch (Exception e) {}
            try { if (connect != null) connect.close(); } catch (Exception e) {}
        }

        return goals;
    }

    // -------- UPDATE GOAL (EMP VALIDATION INCLUDED) --------
    public int updateGoal(Goal goal) throws DatabaseException {

        Connection connect = null;
        PreparedStatement preparedstatement = null;

        String sql =
            "UPDATE goal SET description=?, deadline=?, priority=?, " +
            "progress_percentage=?, status=? WHERE goal_id=? AND emp_id=?";

        try {
            connect = DBUtil.getConnection();
            preparedstatement = connect.prepareStatement(sql);

            preparedstatement.setString(1, goal.getDescription());
            preparedstatement.setDate(2, new java.sql.Date(goal.getDeadline().getTime()));
            preparedstatement.setString(3, goal.getPriority());
            preparedstatement.setInt(4, goal.getProgressPercentage());
            preparedstatement.setString(5, goal.getStatus());
            preparedstatement.setInt(6, goal.getGoalId());
            preparedstatement.setInt(7, goal.getempId());

            return preparedstatement.executeUpdate(); 

        } catch (Exception e) {
            e.printStackTrace(); 
            throw new DatabaseException("Error updating goal");
        } finally {
            try { if (preparedstatement != null) preparedstatement.close(); } catch (Exception e) {}
        }
    }
}