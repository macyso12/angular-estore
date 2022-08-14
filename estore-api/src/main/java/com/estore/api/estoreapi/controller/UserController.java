package com.estore.api.estoreapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.User;

/**
 * UserController handles the request for the User resource
 * 
 * @author: itsTeamTwo
 */
@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());
    private UserDAO userDao;

    public UserController(UserDAO userDao) {
        this.userDao = userDao;
    }

    /**
     * Responds to the GET request for a {@linkplain User user} for the given
     * id
     * 
     * @param id The id used to locate the {@link User user}
     * 
     * @return ResponseEntity with {@link User user} object and HTTP status of
     *         OK if found<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        LOG.info("GET /users/" + id);
        try {
            User user = userDao.getUser(id);
            if (user != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain User user} with the provided user object
     * 
     * @param user - The {@link User user} to create
     * 
     * @return ResponseEntity with created {@link User user} object and HTTP
     *         status of CREATED<br>
     *         ResponseEntity with HTTP status of CONFLICT if {@link User user
     *         } object already exists<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        LOG.info("POST /users " + user);

        try {
            User newUser = userDao.createUser(user);

            if (newUser != null) {
                return new ResponseEntity<>(newUser, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Responds to the GET request for all
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<User[]> getUsers() {
        LOG.info("GET /users");

        try {
            User[] users = userDao.getUsers();
            if (users != null)
                return new ResponseEntity<User[]>(users, HttpStatus.OK);
            else {
                users = new User[0];
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes a {@linkplain User user} with the given id
     * 
     * @param id The id of the {@link User user} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted
     *         ResponseEntity with HTTP status of NOT_FOUND if not found
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        LOG.info("DELETE /users/" + id);

        try {
            Boolean isDelete = userDao.deleteUser(id);

            if (isDelete) {
                return new ResponseEntity<User>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain User users} whose name
     * contains
     * the text in name
     * 
     * @param username The username parameter which contains the text used to find
     *                 the
     *                 {@link User user}
     * 
     * @return ResponseEntity with array of {@link User user} objects (may be
     *         empty) and
     *         HTTP status of OK<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     *         <p>
     *         Example: Find all users that contain the text "ee"
     *         GET http://localhost:8080/users/?username=ee
     */
    @GetMapping("/")
    public ResponseEntity<User[]> searchUser(@RequestParam String username) {
        LOG.info("GET /users/?username=" + username);
        try {
            User[] users = userDao.findUsers(username);
            if (users != null)
                return new ResponseEntity<User[]>(users, HttpStatus.OK);
            else {
                users = new User[0];
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the {@linkplain User user} with the provided
     * {@linkplain User user} object, if it exists
     * 
     * @param user The {@link User user} to update
     * 
     * @return ResponseEntity with updated {@link User user} object and HTTP
     *         status of OK if updated<br>
     *         ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     *         ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        LOG.info("PUT /users " + user);

        try {
            // getting the user
            User updatedUser = userDao.updateUser(user);
            // when user is not null, meaning user found
            if (updatedUser != null) {
                // Update
                return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
