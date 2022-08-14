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

import com.estore.api.estoreapi.persistence.UserDAO;
import com.estore.api.estoreapi.model.UserState;
import com.estore.api.estoreapi.model.User;

/**
 * Test the User Controller class
 * 
 * @author itsTeamTwo
 */

@Tag("Controller-tier")
public class UserControllerTest {
    private UserDAO mockUserDAO;
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
    }

    /**
     * Test for the get user by id
     * 
     * @throws IOException
     */
    @Test
    public void testGetUser() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.getUser((user.getUserID()))).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(user.getUserID());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Test for the get user by id when user not found
     * 
     * @throws IOException
     */
    @Test
    public void testGetUserNotFound() throws IOException {
        int userID = 100;

        when(mockUserDAO.getUser((userID))).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(userID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test for the get product handle exception when an Internal Server Error
     * occurs
     * - Used for when the DAO is not connected/working properly
     * 
     * @throws IOException
     */
    @Test
    public void testGetProductHandleException() throws IOException {
        int userID = 100;

        doThrow(new IOException()).when(mockUserDAO).getUser(userID);

        ResponseEntity<User> response = userController.getUser(userID);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests creating a new user
     * 
     * @throws IOException
     */
    @Test
    public void testCreateUser() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests creating a new user when the user already exists
     * 
     * @throws IOException
     */
    @Test
    public void testCreateUserFailed() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.createUser(user)).thenReturn(null);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    /**
     * Throws IOE when userDAO is not connected
     * 
     * @throws IOException
     */
    @Test
    public void testCreateUserHandleException() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        doThrow(new IOException()).when(mockUserDAO).createUser(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    /**
     * Update user test
     * 
     * @throws IOException
     */
    @Test
    public void testUpdateUser() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.updateUser(user)).thenReturn(user);
        ResponseEntity<User> response = userController.updateUser(user);
        user.setUserName("Chad");

        response = userController.updateUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    /**
     * Tests for failed update, user doesnt exist (no user to update)
     * 
     * @throws IOException
     */
    @Test
    public void testUpdateUserFailed() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.updateUser(user)).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test internal server error if userDAO not connected
     * 
     * @throws IOException
     */
    @Test
    void testUpdateUserHandleException() throws IOException {
        User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        doThrow(new IOException()).when(mockUserDAO).updateUser(user);

        ResponseEntity<User> response = userController.updateUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the get users method
     * 
     * @throws IOException
     */
    @Test
    public void testGetUsers() throws IOException {
        // invoke not found
        ResponseEntity<User[]> response = userController.getUsers();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        User[] users = new User[3];
        users[0] = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);
        users[1] = new User(100, "Chad", "ChadIsCool123", "a name", "email@email.com", true, UserState.LOGGED_OUT);
        users[2] = new User(101, "Tyler", "TylerIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockUserDAO.getUsers()).thenReturn(users);

        response = userController.getUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }

    /**
     * Test case for when UserDAO is not connected
     * 
     * @throws IOException
     */
    @Test
    public void testGetUsersHandleException() throws IOException {
        doThrow(new IOException()).when(mockUserDAO).getUsers();

        ResponseEntity<User[]> response = userController.getUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    /**
     * Tests the search user method (found and not found)
     * 
     * @throws IOException
     */
    @Test
    public void testSearchUsers() throws IOException {
        User[] users = new User[3];
        users[0] = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);
        users[1] = new User(100, "Chad", "ChadIsCool123", "a name", "email@email.com", true, UserState.LOGGED_OUT);

        String searchString = "ad";
        when(mockUserDAO.findUsers(searchString)).thenReturn(users);

        ResponseEntity<User[]> response = userController.searchUser(searchString);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        // Invoke not found
        searchString = "zz";
        response = userController.searchUser(searchString);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test exception handling for search user
     * 
     * @throws IOException
     */
    @Test
    public void testSearchUsersHandleException() throws IOException {
        String searchString = "soup";

        doThrow(new IOException()).when(mockUserDAO).findUsers(searchString);

        ResponseEntity<User[]> response = userController.searchUser(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    /**
     * Tests controller delete user method
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteUser() throws IOException {
        int userID = 99;

        when(mockUserDAO.deleteUser(userID)).thenReturn(true);

        ResponseEntity<User> response = userController.deleteUser(userID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests controller delete when user not found
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteUserNotFound() throws IOException {
        int userID = 99;

        when(mockUserDAO.deleteUser(userID)).thenReturn(false);

        ResponseEntity<User> response = userController.deleteUser(userID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests controller delete method when userDAO not connected
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteUserHandleException() throws IOException {
        int userID = 99;

        doThrow(new IOException()).when(mockUserDAO).deleteUser(userID);

        ResponseEntity<User> response = userController.deleteUser(userID);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    // +++++++++++++++++++++++++++ Login Controller Methods Testing
    // +++++++++++++++++++++++++++++++

    // /**
    //  * Test for the get current method
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testGetCurrentUsers() throws IOException {
    //     User[] users = new User[3];

    //     users[0] = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);
    //     users[1] = new User(100, "Chad", "ChadIsCool123", "a name", "email@email.com", true, UserState.LOGGED_OUT);
    //     users[2] = new User(101, "Tyler", "TylerIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

    //     User[] currentUsers = new User[0];
    //     when(mockUserDAO.getCurrentUsers()).thenReturn(currentUsers);
    //     ResponseEntity<User[]> response = userController.getCurrentUsers();
    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    //     users[0].setUserState(UserState.LOGGED_IN);
    //     users[1].setUserState(UserState.LOGGED_IN);
    //     users[2].setUserState(UserState.LOGGED_IN);

    //     when(mockUserDAO.getCurrentUsers()).thenReturn(users);
    //     response = userController.getCurrentUsers();
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(users, response.getBody());
    // }

    // /**
    //  * Tests the get current when UserDAO not connected
    //  */
    // @Test
    // public void testGetCurrentUsersHandleException() throws IOException {
    //     doThrow(new IOException()).when(mockUserDAO).getCurrentUsers();

    //     ResponseEntity<User[]> response = userController.getCurrentUsers();

    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    // }

    // /**
    //  * Login user test
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLoginUser() throws IOException {
    //     User user = new User(303, "a user", "a password", "a name", "email@email.com", false, UserState.LOGGED_OUT);
    //     // user.setUserState(UserState.LOGGED_IN);

    //     when(mockUserDAO.createUser(user)).thenReturn(user);
    //     // ResponseEntity<User> response = userController.createUser(user);
    //     // assertEquals(HttpStatus.CREATED, response.getStatusCode());

    //     when(mockUserDAO.getUser(user.getUserID())).thenReturn(user);
    //     // response = userController.getUser(user.getUserID());
    //     // assertEquals(HttpStatus.OK, response.getStatusCode());
    //     // need to add a mock for the userDAO.createUser(user) to resolve 404 error
    //     when(mockUserDAO.loginUser(user)).thenReturn(UserState.LOGGED_IN);
    //     ResponseEntity<User> response = userController.loginUser(user);
    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(user.getUserState(), response.getBody().getUserState());
    // }

    // /**
    //  * Tests for failed login, user doesnt exist (no user to update)
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLoginUserFailed() throws IOException {
    //     User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);

    //     user.setUserState(UserState.LOGGED_IN);
    //     when(mockUserDAO.loginUser(user)).thenReturn(null);

    //     ResponseEntity<User> response = userController.loginUser(user);

    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }

    // /**
    //  * Test internal server error if userDAO not connected
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // void testLoginUserHandleException() throws IOException {
    //     User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_OUT);
    //     when(mockUserDAO.createUser(user)).thenReturn(user);
    //     when(mockUserDAO.getUser(user.getUserID())).thenReturn(user);

    //     user.setUserState(UserState.LOGGED_IN);
    //     doThrow(new IOException()).when(mockUserDAO).loginUser(user);

    //     ResponseEntity<User> response = userController.loginUser(user);

    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    // }

    // /**
    //  * Logout user test
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLogoutUser() throws IOException {
    //     User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_IN);
    //     when(mockUserDAO.createUser(user)).thenReturn(user);
    //     when(mockUserDAO.getUser(user.getUserID())).thenReturn(user);

    //     user.setUserState(UserState.LOGGED_OUT);
    //     when(mockUserDAO.logoutUser(user)).thenReturn(user.getUserState());

    //     ResponseEntity<User> response = userController.logOutUser(user);

    //     assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assertEquals(user.getUserState(), response.getBody().getUserState());
    // }

    // /**
    //  * Tests for failed logout, user doesnt exist (no user to update)
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLogoutUserFailed() throws IOException {
    //     User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_IN);
    //     user.setUserState(UserState.LOGGED_OUT);
    //     when(mockUserDAO.logoutUser(user)).thenReturn(null);

    //     ResponseEntity<User> response = userController.logOutUser(user);

    //     assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    // }

    // /**
    //  * Test internal server error if userDAO not connected
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // void testLogoutUserHandleException() throws IOException {
    //     User user = new User(99, "Brad", "BradIsCool123", "a name", "email@email.com", false, UserState.LOGGED_IN);
    //     when(mockUserDAO.createUser(user)).thenReturn(user);
    //     when(mockUserDAO.getUser(user.getUserID())).thenReturn(user);

    //     user.setUserState(UserState.LOGGED_OUT);
    //     doThrow(new IOException()).when(mockUserDAO).logoutUser(user);

    //     ResponseEntity<User> response = userController.logOutUser(user);

    //     assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    // }
}
