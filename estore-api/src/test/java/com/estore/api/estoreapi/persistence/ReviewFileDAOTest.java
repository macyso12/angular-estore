package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for the persistence tier class ReviewFileDAO
 * 
 * @author itsTeamTwo
 */
@Tag("Persistence-tier")
public class ReviewFileDAOTest {
    Review[] testReviews;
    ReviewFileDAO reviewFileDAO;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupReviewFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testReviews = new Review[3];
        testReviews[0] = new Review(99, 13, 23, 5, "Excellent");
        testReviews[1] = new Review(100, 13, 45, 3, "Good");
        testReviews[2] = new Review(101, 100, 34, 1, "Terrible");

        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), Review[].class)).thenReturn(testReviews);
        reviewFileDAO = new ReviewFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test
    public void testGetReview() throws IOException {
        // Invoke
        Review review = reviewFileDAO.getReview(99);

        // Analyze
        assertEquals(reviewFileDAO.getReviews().length, testReviews.length);
        assertEquals(testReviews[0], review);
    }

    @Test
    public void testGetReviewNotFound() throws IOException {
        // Invoke
        Review review = reviewFileDAO.getReview(102);

        // Analyze
        assertEquals(testReviews.length, reviewFileDAO.getReviews().length);
        assertNull(review);
    }

    @Test
    public void testGetReviews() throws IOException {
        // Invoke
        Review[] reviews = reviewFileDAO.getReviews();

        // Analyze
        assertEquals(reviews.length, testReviews.length);
        for (int i = 0; i < reviews.length; i++) {
            assertEquals(testReviews[i], reviews[i]);
        }
    }

    @Test
    public void testSearchReviews() throws IOException {
        // Invoke
        Review[] reviews = reviewFileDAO.searchReviews("Excellent");

        // Analyze
        assertEquals(reviews.length, 1);
        assertEquals(reviews[0], testReviews[0]);
    }

    @Test
    public void testCreateReview() throws IOException {
        Review review = new Review(102, 1, 2, 3, "Good");
        Review result = reviewFileDAO.createReview(review);

        assertNotNull(result);
        Review actual = reviewFileDAO.getReview(102);
        assertEquals(result.getId(), actual.getId());
        assertEquals(result.getUserId(), actual.getUserId());
        assertEquals(result.getProductId(), actual.getProductId());
        assertEquals(result.getRating(), actual.getRating());
        assertEquals(result.getComment(), actual.getComment());

        Review review2 = new Review(104, 3, 45, 1, "Horrible");
        Review result2 = reviewFileDAO.createReview(review2);
        assertNotNull(result2);
    }

    @Test
    public void testDeleteReview() throws IOException {
        assertFalse(reviewFileDAO.deleteReview(102));

        Review review = new Review(102, 1, 2, 3, "Good");
        reviewFileDAO.createReview(review);
        assertTrue(reviewFileDAO.deleteReview(102));
        Review actual = reviewFileDAO.getReview(102);
        assertNull(actual);
    }


    @Test
    public void testSaveException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(Review[].class));
        Review review = new Review(102, 1, 2, 3, "Good");

        // Invoke
        assertThrows(IOException.class, () -> reviewFileDAO.createReview(review), "Excepted exception not thrown");
    }

    @Test
    public void testConstructorException() throws IOException {
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException()).when(mockObjectMapper).readValue(new File("doesnt_matter.txt"), Review[].class);

        assertThrows(IOException.class, () -> new ReviewFileDAO("doesnt_matter.txt",
                mockObjectMapper), "Unexpected exception not thrown");
    }
}
