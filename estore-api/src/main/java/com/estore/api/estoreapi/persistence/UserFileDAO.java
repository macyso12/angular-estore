package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.User;
// import com.estore.api.estoreapi.model.UserState;

/**
 * Implements the functionality for JSON file-based persistance for Users
 * 
 * @author itsTeamTwo
 */

@Component
public class UserFileDAO implements UserDAO {
    Map<Integer, User> users;

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
    public UserFileDAO(@Value("${users.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private User[] getUsersArray(String containsText) {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUserName().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);

        return userArray;
    }

    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        for (User user : userArray) {
            users.put(user.getUserID(), user);
            if (user.getUserID() > nextId) {
                nextId = user.getUserID() + 1;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User[] getUsers() throws IOException {
        synchronized (users) {
            return getUsersArray();
        }

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User[] findUsers(String containsText) throws IOException {
        synchronized (users) {
            return getUsersArray(containsText);
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User getUser(int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(id)) {
                return users.get(id);
            } else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User createUser(User user) throws IOException {
        synchronized (users) {
            User newUser = new User(nextId(), user.getUserName(), user.getPassword(), user.getName(), user.getEmail(),
                    user.isAdmin(), user.getUserState());
            users.put(newUser.getUserID(), newUser);
            save();
            return newUser;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public User updateUser(User user) throws IOException {
        synchronized (user) {
            if (users.containsKey(user.getUserID()) == false)
                return null;

            users.put(user.getUserID(), user);
            save();
            return user;

        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean deleteUser(int id) throws IOException {
        synchronized (users) {
            if (users.containsKey(id)) {
                users.remove(id);
                return save();
            } else {
                return false;
            }
        }
    }

    // /**
    //  * {@inheritDoc}}
    //  */
    // @Override
    // public User[] getCurrentUsers() throws IOException {

    //     ArrayList<User> currentUsers = new ArrayList<>();

    //     for (User user : users.values()) {
    //         if (user.getUserState() == UserState.LOGGED_IN) {
    //             currentUsers.add(user);
    //         }
    //     }
    //     User[] userArray = new User[currentUsers.size()];
    //     currentUsers.toArray(userArray);

    //     return userArray;
    // }

    // /**
    //  * {@inheritDoc}}
    //  */
    // @Override
    // public UserState loginUser(User user) throws IOException {
    //     synchronized (users) {
    //         if (users.containsKey(user.getUserID()) == false) {
    //             return UserState.USER_NOT_FOUND;
    //         }
    //         User loggedInUser = users.get(user.getUserID());
    //         if (user.getUserName() != loggedInUser.getUserName()) {
    //             return UserState.USER_NOT_FOUND;
    //         }
    //         if (user.getPassword() != loggedInUser.getPassword()) {
    //             return UserState.INCORRECT_PASSWORD;
    //         }
    //         loggedInUser.setUserState(UserState.LOGGED_IN);
    //         users.put(user.getUserID(), loggedInUser);
    //         return UserState.LOGGED_IN;
    //     }
    // }

    // /**
    //  * {@inheritDoc}}
    //  */
    // @Override
    // public UserState logoutUser(User user) throws IOException {
    //     synchronized (users) {
    //         if (users.containsKey(user.getUserID()) == false) {
    //             return UserState.USER_NOT_FOUND;
    //         }
    //         // User loggedOutUser = user;
    //         user.setUserState(UserState.LOGGED_OUT);
    //         // users.put(user.getUserID(), loggedOutUser);
    //         return UserState.LOGGED_OUT;
    //     }
    // }

}
