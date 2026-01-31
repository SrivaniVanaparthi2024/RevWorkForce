package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.model.Employee;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

public class PerformanceReviewService {

//    private PerformanceReviewDAO reviewDAO = new PerformanceReviewDAO();
    private PerformanceReviewDAO reviewDAO;
    private EmployeeDAO empDAO;
    
    //  Default constructor (needed for app runtime)
    public PerformanceReviewService() {
        this.reviewDAO = new PerformanceReviewDAO();
        this.empDAO = new EmployeeDAO();
    }

    //  Constructor for TESTING (Mockito)
    public PerformanceReviewService(
            PerformanceReviewDAO reviewDAO,
            EmployeeDAO empDAO) {
        this.reviewDAO = reviewDAO;
        this.empDAO = empDAO;
    }

    public List<PerformanceReview> viewPerformanceReviews(int empId) throws InvalidInputException, DatabaseException {
        if (empId <= 0) {
            throw new InvalidInputException("Invalid employee id");
        }

        return reviewDAO.getReviewsByEmployeeId(empId);
    }
    
 // ---------- MANAGER GIVES REVIEW ----------
    public void giveManagerReview(
            int managerId,
            int empId,
            int reviewYear,
            String feedback,
            int rating)
            throws InvalidInputException, DatabaseException {

        if (empId <= 0 || reviewYear <= 0) {
            throw new InvalidInputException("Invalid employee or year");
        }

        if (feedback == null || feedback.trim().length() == 0) {
            throw new InvalidInputException("Feedback cannot be empty");
        }

        if (rating < 1 || rating > 5) {
            throw new InvalidInputException("Rating must be between 1 and 5");
        }

//        EmployeeDAO empDAO = new EmployeeDAO();
        Employee emp;

        try {
            emp = empDAO.getEmployeeById(empId);
        } catch (Exception e) {
            throw new InvalidInputException("Employee not found");
        }

        // ONLY manager-side validation
        if (emp.getManagerId() == null || emp.getManagerId().intValue() != managerId) {
            throw new InvalidInputException("Employee does not report to you");
        }

        PerformanceReview pr = new PerformanceReview();
        pr.setEmpId(empId);
        pr.setReviewYear(reviewYear);
        pr.setManagerFeedback(feedback);
        pr.setRating(rating);

        reviewDAO.addManagerReview(pr);
    }


   
}

