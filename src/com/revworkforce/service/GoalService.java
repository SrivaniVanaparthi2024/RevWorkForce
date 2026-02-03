package com.revworkforce.service;

import com.revworkforce.dao.GoalDAO;
import com.revworkforce.dao.impl.GoalDAOImpl;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Goal;

import java.util.Date;
import java.util.List;

public class GoalService {

    private GoalDAO goalDAO = new GoalDAOImpl();

    private boolean isPastDate(Date d) {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);
        return d.before(today);
    }

    // -------- ADD GOAL --------
    public void addGoal(int empId, Goal goal)
            throws InvalidInputException, DatabaseException {

        if (goal.getDescription() == null || goal.getDescription().trim().isEmpty()) {
            throw new InvalidInputException("Description cannot be empty");
        }

        if (isPastDate(goal.getDeadline())) {
            throw new InvalidInputException("Deadline must be today or future date");
        }

        goal.setempId(empId);
        goalDAO.addGoal(goal);
    }

    // -------- VIEW GOALS --------
    public List<Goal> viewGoals(int empId) throws DatabaseException {
        return goalDAO.viewGoalsByEmp(empId);
    }

    // -------- UPDATE GOAL --------
    public void updateGoal(int empId, Goal goal)
            throws InvalidInputException, DatabaseException {

        if (goal.getGoalId() <= 0) {
            throw new InvalidInputException("Invalid goal ID");
        }

        if (isPastDate(goal.getDeadline())) {
            throw new InvalidInputException("Deadline must be today or future date");
        }

        goal.setempId(empId);

        int rows = goalDAO.updateGoal(goal);
        if (rows == 0) {
            throw new InvalidInputException("Goal not found or not owned by you");
        }
    }
}