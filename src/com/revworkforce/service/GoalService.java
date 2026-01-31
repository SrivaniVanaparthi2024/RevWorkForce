package com.revworkforce.service;

import com.revworkforce.dao.GoalDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Goal;

import java.util.Date;
import java.util.List;

public class GoalService {

    private GoalDAO goalDAO = new GoalDAO();

    // -------- ADD GOAL --------
    public void addGoal(int empId, Goal goal)
            throws InvalidInputException, DatabaseException {

        if (goal.getDeadline().before(new Date())) {
            throw new InvalidInputException("Deadline must be a future date");
        }

        goal.setempId(empId); // enforce ownership
        goalDAO.addGoal(goal);
    }

    // -------- VIEW GOALS --------
    public List<Goal> viewGoals(int empId) throws DatabaseException {
        return goalDAO.viewGoalsByEmp(empId);
    }

    // -------- UPDATE GOAL --------
    public void updateGoal(int empId, Goal goal)
            throws InvalidInputException, DatabaseException {

        if (goal.getDeadline().before(new Date())) {
            throw new InvalidInputException("Deadline must be a future date");
        }

        goal.setempId(empId); // enforce ownership

        int rows = goalDAO.updateGoal(goal);
        if (rows == 0) {
            throw new InvalidInputException("Goal not found or not owned by you");
        }
    }
}