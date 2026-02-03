package com.revworkforce.service;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.dao.impl.EmployeeDAOImpl;
import com.revworkforce.dao.impl.PerformanceReviewDAOImpl;
import com.revworkforce.model.Employee;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.InvalidInputException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceReviewService {

    private static final Logger logger =
            LoggerFactory.getLogger(PerformanceReviewService.class);

    private PerformanceReviewDAO reviewDAO;
    private EmployeeDAO empDAO;

    // Default constructor
    public PerformanceReviewService() {
        this.reviewDAO = new PerformanceReviewDAOImpl();
        this.empDAO = new EmployeeDAOImpl();
    }

    // Constructor for Mockito testing
    public PerformanceReviewService(
            PerformanceReviewDAOImpl reviewDAO,
            EmployeeDAOImpl empDAO) {
        this.reviewDAO = reviewDAO;
        this.empDAO = empDAO;
    }

    // ---------------- VIEW EMPLOYEE REVIEWS ----------------
    public List<PerformanceReview> viewPerformanceReviews(int empId)
            throws InvalidInputException, DatabaseException {

        if (empId <= 0) {
            logger.warn("Invalid employee ID for viewing reviews: {}", empId);
            throw new InvalidInputException("Invalid employee id");
        }

        logger.info("Fetching performance reviews for empId={}", empId);
        return reviewDAO.getReviewsByEmployeeId(empId);
    }

    // ---------------- MANAGER GIVES REVIEW ----------------
    public void giveManagerReview(
            int managerId,
            int empId,
            int reviewYear,
            String feedback,
            int rating)
            throws InvalidInputException, DatabaseException {

        logger.info("Manager {} giving review for empId={} year={}", managerId, empId, reviewYear);

        if (empId <= 0 || reviewYear <= 0) {
            logger.warn("Invalid employee ID or review year: empId={}, year={}", empId, reviewYear);
            throw new InvalidInputException("Invalid employee or year");
        }

        if (feedback == null || feedback.trim().length() == 0) {
            logger.warn("Feedback is empty for empId={}", empId);
            throw new InvalidInputException("Feedback cannot be empty");
        }

        if (rating < 1 || rating > 5) {
            logger.warn("Invalid rating {} for empId={}", rating, empId);
            throw new InvalidInputException("Rating must be between 1 and 5");
        }

        Employee emp;
        try {
            emp = empDAO.getEmployeeById(empId);
            if (emp == null) {
                logger.warn("Employee not found with empId={}", empId);
                throw new InvalidInputException("Employee not found");
            }
        } catch (Exception e) {
            logger.error("Error fetching employee with empId={}: {}", empId, e.getMessage());
            throw new InvalidInputException("Employee not found");
        }

        if (emp.getManagerId() == null || emp.getManagerId().intValue() != managerId) {
            logger.warn("Employee empId={} does not report to manager {}", empId, managerId);
            throw new InvalidInputException("Employee does not report to you");
        }

        PerformanceReview pr = new PerformanceReview();
        pr.setEmpId(empId);
        pr.setReviewYear(reviewYear);
        pr.setManagerFeedback(feedback);
        pr.setRating(rating);

        reviewDAO.addManagerReview(pr);
        logger.info("Review added successfully for empId={} by manager {}", empId, managerId);
    }
}