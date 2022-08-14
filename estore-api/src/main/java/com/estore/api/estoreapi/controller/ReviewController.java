package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.ReviewDAO;
import com.estore.api.estoreapi.model.Review;

/**
 * ReviewController handles the request for the Product resource
 * 
 * @author itsTeamTwo
 */
@RestController
@RequestMapping("reviews")
public class ReviewController {
    private static final Logger LOG = Logger.getLogger(ReviewController.class.getName());
    private ReviewDAO reviewDao;

    public ReviewController(ReviewDAO reviewDao) {
        this.reviewDao = reviewDao;
    }

    /**
     * Responds to the GET request for a {@linkplain Review review} for the given
     * 
     * @param id The id used to locate the {@link Review review}
     * 
     * @return ResponseEntity with {@link Review review} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable int id) {
        LOG.info("GET /reviews/" + id);
        try {
            Review review = reviewDao.getReview(id);
            if (review != null)
                return new ResponseEntity<Review>(review, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all
     * 
     * @return ResponseEntity with array of {@link Review review} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Review[]> getReviews() {
        LOG.info("GET /reviews");
        try {
            Review[] reviews = reviewDao.getReviews();
            if (reviews != null)
                return new ResponseEntity<Review[]>(reviews, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Review review} with the provided review object
     * 
     * @param review - The {@link Review review} to create
     * 
     * @return ResponseEntity with created {@link Review review} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link Review
     *         review} object already exists<br>
     *         Note: one user can only create one review per product<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        LOG.info("POST /reviews" + review);
        try {
            Review newReview = reviewDao.createReview(review);
            if (newReview != null)
                return new ResponseEntity<Review>(newReview, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the {@linkplain Review review} with the given id
     * 
     * @param id - The id used to locate the {@link Review review}
     * 
     * @return ResponseEntity with HTTP status of OK if deleted<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {
        LOG.info("DELETE /reviews/" + id);
        try {
            if (reviewDao.deleteReview(id))
                return new ResponseEntity<>(HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get the {@linkplain Review review} for the given string
     * 
     * @param containStr - The string used to locate the {@link Review review}
     * @return ResponseEntity with array of {@link Review review} objects (may be
     *         empty) and HTTP status of OK <br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     * @throws IOException if an error occurs while reading from the database
     */
    @GetMapping("/")
    public ResponseEntity<Review[]> searchReviews(@RequestParam String containStr) {
        LOG.info("GET /reviews/?containStr=" + containStr);
        try {
            Review[] reviews = reviewDao.searchReviews(containStr);
            if (reviews != null)
                return new ResponseEntity<Review[]>(reviews, HttpStatus.OK);
            else {
                reviews = new Review[0];
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
