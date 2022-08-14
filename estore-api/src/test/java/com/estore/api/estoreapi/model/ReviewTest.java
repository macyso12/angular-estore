package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Test the Review class
 * 
 * @author isTeamTwo
 */
@Tag("Model-tier")
public class ReviewTest {
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        int expected_productId = 1;
        int expected_userId = 1;
        int expected_rating = 5;
        String expected_comment = "This is a comment";

        // Invoke
        Review review = new Review(expected_id, expected_productId, expected_userId, expected_rating, expected_comment);

        // Analyze
        assertEquals(expected_id, review.getId());
        assertEquals(expected_productId, review.getProductId());
        assertEquals(expected_userId, review.getUserId());
        assertEquals(expected_rating, review.getRating());
        assertEquals(expected_comment, review.getComment());
    }

    @Test
    public void testToString() {
        // Setup
        int id = 99;
        int productId = 1;
        int userId = 1;
        int rating = 5;
        String comment = "This is a comment";
        Review review = new Review(id, productId, userId, rating, comment);

        // Invoke
        String expected_string = String.format(Review.STRING_FORMAT, id, productId, userId, rating, comment);

        // Analyze
        assertEquals(expected_string, review.toString());
    }
}
