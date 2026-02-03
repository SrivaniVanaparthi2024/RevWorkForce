package com.revworkforce.dao;

import com.revworkforce.model.PerformanceReview;
import com.revworkforce.exception.DatabaseException;

import java.util.List;

public interface PerformanceReviewDAO {

    List<PerformanceReview> getReviewsByEmployeeId(int empId)
            throws DatabaseException;

    void addManagerReview(PerformanceReview review)
            throws DatabaseException;
}