package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

// import com.estore.api.estoreapi.model.UserState;
import com.estore.api.estoreapi.model.User;
import com.estore.api.estoreapi.model.UserState;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test for the persistence tier class UserFileDAO
 * 
 * @author isTeamTwo
 */
@Tag("Persistence-tier")
public class UserFileDAOTest {
    User[] testUsers;
    UserFileDAO userFileDAO;
    ObjectMapper mockObjectMapper;

    /**
     * Setup data for each test
     * 
     * @throws IOException
     */
    @BeforeEach
    public void setupUserFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testUsers = new User[3];
        testUsers[0] = new User(99, "Joe", "1234", "a name", "email@email.com", true, UserState.LOGGED_OUT);
        testUsers[1] = new User(100, "Scarlett", "3432", "a name", "email@email.com", false, UserState.LOGGED_OUT);
        testUsers[2] = new User(101, "Jason", "3432", "a name", "email@email.com", false, UserState.LOGGED_OUT);

        when(mockObjectMapper.readValue(new File("doesnt_matter.txt"), User[].class)).thenReturn(testUsers);
        userFileDAO = new UserFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    /**
     * test getUser(id)
     * 
     * @throws IOException
     */
    @Test
    public void testGetUser() throws IOException {
        // Invoke
        User user = userFileDAO.getUser(99);

        // Analyze
        assertEquals(userFileDAO.getUsers().length, testUsers.length);
        assertEquals(user, testUsers[0]);
    }

    /**
     * test getUsers()
     * 
     * @throws IOException
     */
    @Test
    public void testGetUsers() throws IOException {
        // Invoke
        User[] users = userFileDAO.getUsers();

        // Analyze
        assertEquals(users.length, testUsers.length);
        for (int i = 0; i < users.length; i++) {
            assertEquals(users[i], testUsers[i]);
        }
    }

    /**
     * test findUsers(containsText)
     */
    @Test
    public void testFindUsers() throws IOException {
        // Invoke
        User[] users = userFileDAO.findUsers("Joe");

        // Analyze
        assertEquals(users.length, 1);
        assertEquals(users[0], testUsers[0]);
    }

    /**
     * test createUser(user)
     * 
     * @throws IOException
     */
    @Test
    public void testCreateUser() throws IOException {
        // Invoke
        User user = new User(102, "Alice", "1234", "a name", "email@email.com", true, UserState.LOGGED_OUT);
        // userFileDAO.createUser(user);

        // Invoke
        User result = assertDoesNotThrow(() -> userFileDAO.createUser(user), "Unexpected exception");

        // Analyze
        assertNotNull(result);
        User actual = userFileDAO.getUser(102);
        assertEquals(actual.getUserID(), user.getUserID());
        assertEquals(actual.getUserName(), user.getUserName());
        assertEquals(actual.getPassword(), user.getPassword());
    }

    /**
     * test updateUser(user)
     * 
     * @throws IOException
     */
    @Test
    public void testUpdateUser() throws IOException {
        // Invoke
        User user = new User(99, "Joe", "1234", "a name", "email@email.com", true, UserState.LOGGED_OUT);
        userFileDAO.updateUser(user);

        // Analyze
        assertEquals(userFileDAO.getUser(99), user);
    }

    /**
     * test deleteUser(id)
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteUser() throws IOException {
        // Invoke
        userFileDAO.deleteUser(99);

        // Analyze
        assertNull(userFileDAO.getUser(99));
    }

    /**
     * test save()
     * 
     * @throws IOException
     */
    @Test
    public void testSaveException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockObjectMapper).writeValue(any(File.class), any(User[].class));
        User user = new User(102, "Matthew", "89342", "a name", "email@email.com", true, UserState.LOGGED_OUT);
        // Invoke
        assertThrows(IOException.class, () -> userFileDAO.createUser(user), "Excepted exception not thrown");
    }

    /**
     * test getUser(id) when id is not found
     * 
     * @throws IOException
     */
    @Test
    public void testGetUserNotFound() throws IOException {
        // Invoke
        User user = userFileDAO.getUser(98);

        // Analyze: same as assertEquals(user, null);
        assertNull(user);
    }

    /**
     * test updateUser(user) when the user is not found
     * 
     * @throws IOException
     */
    @Test
    public void testUpdateUserNotFound() throws IOException {
        // Invoke
        User user = new User(98, "Matthew", "89342", "a name", "email@email.com", true, UserState.LOGGED_OUT);

        User result = assertDoesNotThrow(() -> userFileDAO.updateUser(user), "Unexpected exception thrown");

        // Analyze: same as assertEquals(user, null);
        assertNull(result);
    }

    /**
     * test findUser(containStr) when the user is not found
     * 
     * @throws IOException
     */
    @Test
    public void testFindUsersNotFound() throws IOException {
        // Invoke
        User[] users = userFileDAO.findUsers("Alice");

        // Analyze
        assertEquals(users.length, 0);
    }

    /**
     * test deleteUser(id) when the user is not found
     * 
     * @throws IOException
     */
    @Test
    public void testDeleteUserNotFound() throws IOException {
        // Invoke
        userFileDAO.deleteUser(98);

        // Analyze
        assertEquals(userFileDAO.getUsers().length, testUsers.length);
    }

    // /**
    //  * test getCurrentUser()
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testGetCurrentUsers() throws IOException {
    //     // Invoke
    //     testUsers[0].setUserState(UserState.LOGGED_IN);
    //     User[] users = userFileDAO.getCurrentUsers();

    //     // Analyze
    //     assertEquals(users.length, 1);
    // }

    // /**
    //  * test login user
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLoginUser() throws IOException {
    //     // Invoke
    //     UserState userState = userFileDAO.loginUser(testUsers[0]);

    //     // Analyze
    //     assertEquals(userState, userFileDAO.getUser(99).getUserState());
    // }

    // /**
    //  * test login user but the user is not found
    //  */
    // @Test
    // public void testLoginUserNotFound() throws IOException {
    //     // Invoke
    //     UserState userState1 = userFileDAO
    //             .loginUser(new User(98, "Matthew", "89342", "a name", "email@email.com", true, UserState.LOGGED_OUT));
    //     UserState userState2 = userFileDAO
    //             .loginUser(new User(99, "Matthew", "89342", "a name", "email@email.com", true, UserState.LOGGED_OUT));

    //     // Analyze
    //     assertEquals(userState1, UserState.USER_NOT_FOUND);
    //     assertEquals(userState2, UserState.USER_NOT_FOUND);
    // }

    // /**
    //  * test login user with wrong password
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLoginUserIncorrectPassword() throws IOException {
    //     // Invoke
    //     UserState userState = userFileDAO
    //             .loginUser(new User(100, "Scarlett", "2222", "a name", "email@email.com", false, UserState.LOGGED_OUT));
    //     // Analyze
    //     assertEquals(userState, UserState.INCORRECT_PASSWORD);
    // }

    // /**
    //  * test logout user
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLogoutUser() throws IOException {
    //     // Invoke
    //     UserState loginState = userFileDAO.logoutUser(testUsers[0]);

    //     // Analyze
    //     assertEquals(loginState, userFileDAO.getUser(99).getUserState());
    // }

    // /**
    //  * test logout user but the user is not found
    //  * 
    //  * @throws IOException
    //  */
    // @Test
    // public void testLogoutUserNotFound() throws IOException {
    //     // Invoke
    //     UserState loginState = userFileDAO
    //             .logoutUser(new User(98, "Matthew", "89342", "a name", "email@email.com", true, UserState.LOGGED_OUT));

    //     // Analyze
    //     assertEquals(loginState, UserState.USER_NOT_FOUND);
    // }

    /**
     * test the controller constructor
     * 
     * @throws IOException
     */
    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);

        doThrow(new IOException()).when(mockObjectMapper).readValue(new File("doesnt_matter.txt"), User[].class);

        assertThrows(IOException.class, () -> new UserFileDAO("doesnt_matter.txt", mockObjectMapper),
                "Unexpected exception not thrown");

    }
}
