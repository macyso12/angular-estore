package com.estore.api.estoreapi.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.estore.api.estoreapi.model.Review;
import com.estore.api.estoreapi.persistence.ReviewDAO;

/**
 * Test the ReviewController class.
 * 
 * @author itsTeamTwo
 */
@Tag("Controller-tier")
public class ReviewControllerTest {
    private ReviewDAO reviewDao;
    private ReviewController reviewController;

    @BeforeEach
    public void setupReviewFileDAO() {
        reviewDao = mock(ReviewDAO.class);
        reviewController = new ReviewController(reviewDao);
    }

    @Test
    public void testGetReview() throws IOException {
        ResponseEntity<Review> response = reviewController.getReview(100);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Review review = new Review(100, 13, 45, 3, "Good");
        when(reviewDao.getReview(100)).thenReturn(review);
        response = reviewController.getReview(100);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());

        doThrow(new IOException()).when(reviewDao).getReview(100);
        response = reviewController.getReview(100);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testGetReviews() throws IOException {
        ResponseEntity<Review[]> response = reviewController.getReviews();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        Review[] reviews = new Review[3];
        reviews[0] = new Review(99, 13, 23, 5, "Excellent");
        reviews[1] = new Review(100, 13, 45, 3, "Good");
        reviews[2] = new Review(101, 100, 34, 1, "Terrible");
        when(reviewDao.getReviews()).thenReturn(reviews);
        response = reviewController.getReviews();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());

        doThrow(new IOException()).when(reviewDao).getReviews();
        response = reviewController.getReviews();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    @Test
    public void testSearchReviews() throws IOException {
        Review[] reviews = new Review[3];
        reviews[0] = new Review(99, 13, 23, 5, "Excellent");
        reviews[1] = new Review(100, 13, 45, 3, "Good");
        reviews[2] = new Review(101, 100, 34, 1, "Terrible");

        ResponseEntity<Review[]> response = reviewController.searchReviews("wonderful");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        when(reviewDao.searchReviews("Good")).thenReturn(reviews);
        response = reviewController.searchReviews("Good");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
        
        doThrow(new IOException()).when(reviewDao).searchReviews("Good");
        response = reviewController.searchReviews("Good");
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateReview() throws IOException {
        ResponseEntity<Review> response = reviewController.createReview(new Review(100, 13, 45, 3, "Good"));
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        Review review = new Review(100, 13, 45, 3, "Good");
        when(reviewDao.createReview(review)).thenReturn(review);
        response = reviewController.createReview(review);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(review, response.getBody());

        doThrow(new IOException()).when(reviewDao).createReview(review);
        response = reviewController.createReview(review);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteReview() throws IOException {
        ResponseEntity<Review> response = reviewController.deleteReview(100);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        
        when(reviewDao.deleteReview(100)).thenReturn(true);
        response = reviewController.deleteReview(100);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        doThrow(new IOException()).when(reviewDao).deleteReview(100);
        response = reviewController.deleteReview(100);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
