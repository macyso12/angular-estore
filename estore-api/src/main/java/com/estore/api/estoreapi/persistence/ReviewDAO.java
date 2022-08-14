package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import com.estore.api.estoreapi.model.Review;

/**
 * ReviewDAO is an interface for Review object persistance
 * 
 * @author itsTeamTwo
 */

/**
 * - Get all reviews
 * - Get review by user id
 * - Get review by product id
 * - Get review by rating
 * - search reviews by comment
 * Note the below searches can be combined into one search
 * Such as get reviews by user id and product id
 */

public interface ReviewDAO {
  /**
   * Get review by id
   * 
   * @return Review object
   * 
   * @throws IOException when file cannot be accessed or read from
   */
  Review getReview(int id) throws IOException;

  /**
   * Retrieves all {@linkplain Review Reviews}
   * 
   * @return An array of {@link Review Reviews} objects, may be empty
   * 
   * @throws IOException if an issue with underlying storage
   */
  Review[] getReviews() throws IOException;

  /**
   * Retrieves all {@linkplain Review Reviews} by search term
   * 
   * @param containsTest
   * @return An array of {@link Review Reviews} objects, may be empty
   * @throws IOException
   */
  Review[] searchReviews(String containsTest) throws IOException;


  /**
   * Creates and saves a {@linkplain Review Review}
   * 
   * @param Review {@linkplain Review Review} object to be created and saved
   *               <br>
   *               The id of the Review object is ignored and a new unique id is
   *               assigned
   *               <br>
   *               The name of the Review object is compared to existing
   *               Reviews to ensure it is unique
   *               <br>
   *               One user can only leave one review per product
   *
   * @return new {@link Review Review} if successful, false otherwise
   * 
   * @throws IOException if an issue with underlying storage
   */
  Review createReview(Review Review) throws IOException;

  /**
   * Deletes a {@linkplain Review Review} with the given id
   * 
   * @param id The id of the {@link Review Review}
   * 
   * @return true if the {@link Review Review} was deleted
   *         <br>
   *         false if Review with the given id does not exist
   * 
   * @throws IOException if underlying storage cannot be accessed
   */
  boolean deleteReview(int id) throws IOException;
}