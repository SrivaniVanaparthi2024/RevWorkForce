package com.revworkforce.service.test;

import com.revworkforce.dao.EmployeeDAO;
import com.revworkforce.dao.PerformanceReviewDAO;
import com.revworkforce.exception.DatabaseException;
import com.revworkforce.exception.EmployeeNotFoundException;
import com.revworkforce.exception.InvalidInputException;
import com.revworkforce.model.Employee;
import com.revworkforce.model.PerformanceReview;
import com.revworkforce.service.PerformanceReviewService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PerformanceReviewServiceTest {

    @Mock
    private PerformanceReviewDAO reviewDAO;

    @Mock
    private EmployeeDAO empDAO;

    @InjectMocks
    private PerformanceReviewService reviewService;

    private PerformanceReview pr1;
    private PerformanceReview pr2;
    private Employee emp;

    @Before
    public void setUp() throws Exception {
        pr1 = new PerformanceReview();
        pr1.setEmpId(3);
        pr1.setReviewYear(2025);
        pr1.setManagerFeedback("Good job");
        pr1.setRating(4);

        pr2 = new PerformanceReview();
        pr2.setEmpId(3);
        pr2.setReviewYear(2024);
        pr2.setManagerFeedback("Needs improvement");
        pr2.setRating(3);

        emp = new Employee();
        emp.setEmpId(3);
        emp.setManagerId(2);
    }

    // ---------- VIEW PERFORMANCE REVIEWS SUCCESS ----------
    @Test
    public void testViewPerformanceReviewsSuccess() throws InvalidInputException, DatabaseException {
        List<PerformanceReview> mockList = Arrays.asList(pr1, pr2);
        when(reviewDAO.getReviewsByEmployeeId(3)).thenReturn(mockList);

        List<PerformanceReview> result = reviewService.viewPerformanceReviews(3);

        verify(reviewDAO, times(1)).getReviewsByEmployeeId(3);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test(expected = InvalidInputException.class)
    public void testViewPerformanceReviewsInvalidEmpId() throws InvalidInputException, DatabaseException {
        reviewService.viewPerformanceReviews(0);
    }

    // ---------- GIVE MANAGER REVIEW SUCCESS ----------
    @Test
    public void testGiveManagerReviewSuccess() throws InvalidInputException, DatabaseException, EmployeeNotFoundException {
        when(empDAO.getEmployeeById(3)).thenReturn(emp);

        reviewService.giveManagerReview(
                2,          // managerId
                3,          // empId
                2025,       // review year
                "Excellent work",
                5           // rating
        );

        verify(empDAO, times(1)).getEmployeeById(3);
        verify(reviewDAO, times(1)).addManagerReview(any(PerformanceReview.class));
    }

    // ---------- INVALID EMPLOYEE ID ----------
    @Test(expected = InvalidInputException.class)
    public void testGiveManagerReviewInvalidEmpId() throws InvalidInputException, DatabaseException {
        reviewService.giveManagerReview(
                2,
                0,
                2025,
                "Invalid employee",
                3
        );
    }

    // ---------- INVALID RATING ----------
    @Test(expected = InvalidInputException.class)
    public void testGiveManagerReviewInvalidRating() throws InvalidInputException, DatabaseException, EmployeeNotFoundException {
        when(empDAO.getEmployeeById(3)).thenReturn(emp);

        reviewService.giveManagerReview(
                2,
                3,
                2025,
                "Invalid rating",
                7
        );
    }

    // ---------- EMPTY FEEDBACK ----------
    @Test(expected = InvalidInputException.class)
    public void testGiveManagerReviewEmptyFeedback() throws InvalidInputException, DatabaseException, EmployeeNotFoundException {
        when(empDAO.getEmployeeById(3)).thenReturn(emp);

        reviewService.giveManagerReview(
                2,
                3,
                2025,
                "",
                3
        );
    }

    // ---------- EMPLOYEE NOT REPORTING TO MANAGER ----------
    @Test(expected = InvalidInputException.class)
    public void testGiveManagerReviewWrongManager() throws InvalidInputException, DatabaseException, EmployeeNotFoundException {
        Employee empWrongManager = new Employee();
        empWrongManager.setEmpId(3);
        empWrongManager.setManagerId(99);

        when(empDAO.getEmployeeById(3)).thenReturn(empWrongManager);

        reviewService.giveManagerReview(
                2,
                3,
                2025,
                "Unauthorized manager",
                3
        );
    }
}