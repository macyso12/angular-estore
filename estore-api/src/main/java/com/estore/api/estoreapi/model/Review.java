package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Review class represents a Review Entity that
 * is associated with a Product Entity and a User Entity
 * 
 * Note: no manipulators functions for properties of the review
 *       because review should be read-only
 * 
 * Reviews have the following attributes
 * id
 * productId
 * userId
 * 
 * @author itsTeamTwo
 */
public class Review {
    // print format for the review
    final static String STRING_FORMAT = "Review[id=%d, productId=%d, userId=%d, rating=%d, comment=%s]";
    
    // properties of the Review
    @JsonProperty("id")
    private int id;
    @JsonProperty("productId")
    private int productId;
    @JsonProperty("userId")
    private int userId;
    @JsonProperty("rating")
    private int rating;
    @JsonProperty("comment")
    private String comment;

    /**
     * Creates a review
     * 
     * @param id         The id of the review
     * @param productId  The id of the product
     * @param userId     The id of the user
     * @param rating     The rating of the review
     * @param review     The comment of the review
     */
    public Review(@JsonProperty("id") int id, 
                  @JsonProperty("productId") int productId, 
                  @JsonProperty("userId") int userId,
                  @JsonProperty("rating") int rating, 
                  @JsonProperty("comment") String comment) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    /**
     * Gets the review id
     * 
     * @return review id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the product id
     * 
     * @return product id
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Gets the user id
     * 
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the rating
     * 
     * @return rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * Gets the comment
     * 
     * @return comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Returns a string representation of the review
     * 
     * @return a string representation of the review
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, productId, userId, rating, comment);
    }
}
