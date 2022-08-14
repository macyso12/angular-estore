package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the model class User
 * 
 * @author isTeamTwo
 */
@Tag("Model-tier")
public class UserTest {
    /**
     * Test the constructor and the all getters
     */
    @Test
    public void testCtor() {
        // Setup
        int expected_id = 99;
        String expected_username = "serrini";
        String expected_password = "19841224";
        String expected_name = "a name";
        String expected_email = "email@email.com";
        boolean expected_isAdmin = true;
        UserState expected_userState = UserState.LOGGED_OUT;

        // Invoke
        User user = new User(expected_id, expected_username, expected_password, expected_name, expected_email,
                expected_isAdmin, expected_userState);

        // Analyze
        assertEquals(expected_id, user.getUserID());
        assertEquals(expected_username, user.getUserName());
        assertEquals(expected_password, user.getPassword());
        assertEquals(expected_name, user.getName());
        assertEquals(expected_email, user.getEmail());
        assertEquals(expected_isAdmin, user.isAdmin());
        assertEquals(expected_userState, user.getUserState());
    }

    /**
     * Test the setter for userName
     */
    @Test
    public void testUserName() {
        // Setup
        int id = 99;
        String username = "serrini";
        String password = "19841224";
        String name = "a name";
        String email = "email@email.com";
        boolean isAdmin = true;
        UserState uState = UserState.LOGGED_OUT;
        User user = new User(id, username, password, name, email, isAdmin, uState);

        String expected_username = "paul";

        // Invoke
        user.setUserName(expected_username);

        // Analyze
        assertEquals(expected_username, user.getUserName());
    }

    /**
     * Test the setter for password
     */
    @Test
    public void testPassword() {
        // Setup
        int id = 99;
        String username = "serrini";
        String password = "19841224";
        String name = "a name";
        String email = "email@email.com";
        boolean isAdmin = true;
        UserState uState = UserState.LOGGED_OUT;
        User user = new User(id, username, password, name, email, isAdmin, uState);

        String expected_password = "42214891";

        // Invoke
        user.setPassword(expected_password);

        // Analyze
        assertEquals(expected_password, user.getPassword());
    }

    /**
     * Test the setter for name and email of the user
     */
    @Test
    public void testNameEmail() {
        // Setup
        int id = 99;
        String username = "serrini";
        String password = "19841224";
        String name = "a name";
        String email = "email@email.com";
        boolean isAdmin = false;
        UserState uState = UserState.LOGGED_OUT;
        User user = new User(id, username, password, name, email, isAdmin, uState);

        String expected_name = "a new name";
        user.setName(expected_name);
        assertEquals(expected_name, user.getName());

        String expected_email = "newEmail@email.com";
        user.setEmail(expected_email);
        assertEquals(expected_email, user.getEmail());
    }

    /**
     * Test the setter for user state
     */
    @Test
    public void testUserState() {
        // Setup
        int id = 99;
        String username = "serrini";
        String password = "19841224";
        String name = "a name";
        String email = "email@email.com";
        boolean isAdmin = true;
        UserState uState = UserState.LOGGED_OUT;
        User user = new User(id, username, password, name, email, isAdmin, uState);

        UserState expected_userState = UserState.LOGGED_IN;

        // Invoke
        user.setUserState(expected_userState);

        // Analyze
        assertEquals(expected_userState, user.getUserState());
    }

    /**
     * Test the string representation of the user
     */
    @Test
    public void testToString() {
        // Setup
        int id = 99;
        String username = "serrini";
        String password = "19841224";
        String name = "a name";
        String email = "email@email.com";
        boolean isAdmin = true;
        UserState uState = UserState.LOGGED_OUT;
        User user = new User(id, username, password, name, email, isAdmin, uState);
        String expected_toString = String.format(User.STRING_FORMAT, id, username, password,
                name, email, isAdmin, user.getUserState().toString());

        // Invoke
        String actual_toString = user.toString();

        // Analyze
        assertEquals(expected_toString, actual_toString);
    }
}
