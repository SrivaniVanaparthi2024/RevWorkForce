package com.revworkforce.model;

public class PerformanceReview {

    private int reviewId;
    private int empId;
    private int reviewYear;
    private String selfReview;
    private String managerFeedback;
    private int rating;

    public PerformanceReview() {
    }

    public PerformanceReview(int reviewId, int empId, int reviewYear,
                             String selfReview, String managerFeedback, int rating) {
        this.reviewId = reviewId;
        this.empId = empId;
        this.reviewYear = reviewYear;
        this.selfReview = selfReview;
        this.managerFeedback = managerFeedback;
        this.rating = rating;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getReviewYear() {
        return reviewYear;
    }

    public void setReviewYear(int reviewYear) {
        this.reviewYear = reviewYear;
    }

    public String getSelfReview() {
        return selfReview;
    }

    public void setSelfReview(String selfReview) {
        this.selfReview = selfReview;
    }

    public String getManagerFeedback() {
        return managerFeedback;
    }

    public void setManagerFeedback(String managerFeedback) {
        this.managerFeedback = managerFeedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

