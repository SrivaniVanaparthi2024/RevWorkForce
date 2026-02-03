package com.revworkforce.dao;

import com.revworkforce.exception.DatabaseException;
import com.revworkforce.model.Goal;

import java.util.List;

public interface GoalDAO {

    void addGoal(Goal goal) throws DatabaseException;

    List<Goal> viewGoalsByEmp(int empId) throws DatabaseException;

    int updateGoal(Goal goal) throws DatabaseException;
}