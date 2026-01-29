package com.revworkforce.service;

import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

public class PerformanceReviewService {

    private PerformanceReviewDAO reviewDAO = new PerformanceReviewDAO();

    public List<PerformanceReview> viewPerformanceReviews(int empId) throws InvalidInputException, DatabaseException {
        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee id");
        }

        return reviewDAO.getReviewsByEmployeeId(empId);
    }
}

