package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Review;

/**
 * Implement the functionality for JSON file-based persistance for Reviews
 * 
 * @author isTeamTwo
 */
@Component
public class ReviewFileDAO implements ReviewDAO {
    Map<Integer, Review> reviews;

    private ObjectMapper objectMapper;
    private static int nextId;
    private String filename;

    /**
     * Constructor
     * 
     * @param filename     The filename of the JSON file
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ReviewFileDAO(@Value("${reviews.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    /**
     * Generates the next id for a new {@linkplain Review Review}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Review Reviews} from the tree map
     * 
     * The contains text will be filtered with reviews' string
     * 
     * @return The array of {@link Review Reviews}, may be empty
     */
    private Review[] getReviewsArray(String containsText) { // if containsText == null, no filter
        ArrayList<Review> reviewArrayList = new ArrayList<>();

        for (Review review : reviews.values()) {
            if (containsText == null || review.toString().contains(containsText)) {
                reviewArrayList.add(review);
            }
        }

        Review[] reviewArray = new Review[reviewArrayList.size()];
        reviewArrayList.toArray(reviewArray);
        return reviewArray;
    }

    /**
     * Generates an array of {@linkplain Review reviews} from the tree map
     * 
     * @return The array of {@link Review reviews}, may be empty
     */
    private Review[] getReviewsArray() {
        return getReviewsArray(null);
    }

    /**
     * Saves the {@linkplain Review reviews} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Review reviews} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Review[] reviewArray = getReviewsArray();

        objectMapper.writeValue(new File(filename), reviewArray);
        return true;
    }

    /**
     * Loads {@linkplain Review reviews} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */

    private boolean load() throws IOException {
        reviews = new TreeMap<>();
        nextId = 0;

        Review[] reviewArray = objectMapper.readValue(new File(filename), Review[].class);

        for (Review review : reviewArray) {
            reviews.put(review.getId(), review);
            if (review.getId() > nextId)
                // Make the next id one greater than the maximum from the file
                nextId = review.getId() + 1;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review getReview(int id) {
        return reviews.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review[] searchReviews(String containsText) {
        return getReviewsArray(containsText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Review[] getReviews() {
        synchronized (reviews) {
            return getReviewsArray();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Review createReview(Review review) throws IOException {
        synchronized (reviews) {
            Review newReview = new Review(nextId(), review.getProductId(), review.getUserId(), review.getRating(),
                    review.getComment());
            reviews.put(newReview.getId(), newReview);
            save(); // may throw an IOException
            return newReview;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteReview(int id) throws IOException {
        synchronized (reviews) {
            if (reviews.containsKey(id)) {
                reviews.remove(id);
                return save();
            } else
                return false;
        }
    }
}
