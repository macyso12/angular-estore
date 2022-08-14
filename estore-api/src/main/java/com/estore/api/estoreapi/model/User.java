package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User class represents a User Entity with the following attributes
 * id
 * userName
 * password
 * isAdmin
 * name
 * email
 * userState (default is LOGGED_OUT)
 * 
 * @author isTeamTwo
 */
public class User {
    // properties of the User
    static final String STRING_FORMAT = "User[id=%d, userName=%s, password=%s, name=%s, email=%s, isAdmin=%b, userState=%s]";

    @JsonProperty("id")
    private int id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("isAdmin")
    private boolean isAdmin;
    @JsonProperty(value = "userState")
    private UserState userState;

    /**
     * Creates a user
     * 
     * @param id        The id of the user
     * @param username  The name of the user
     * @param password  The password of the user
     * @param name      The name of the user
     * @param email     The email of the user
     * @param isAdmin   Whether the user is an admin or not
     * @param userState The state of the user
     */
    public User(
            @JsonProperty("id") int id,
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("isAdmin") boolean isAdmin,
            @JsonProperty("userState") UserState uState) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
        this.userState = uState;

        // Injectable cart component, decreasing dependency for testing
        // if (!isAdmin) {
        //     Cart userCart = new Cart(username);
        // }
    }

    /**
     * Get the id of the user
     * 
     * @return The id of the user
     */
    public int getUserID() {
        return this.id;
    }

    /**
     * Get the user name of the user
     *
     * @return The user name of the user
     */
    public String getUserName() {
        return this.username;
    }

    /**
     * Get the password of the user
     * 
     * @return The password of the user
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Check if the user is an admin
     * 
     * @return True if the user is an admin, false otherwise
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Get the name of the user
     * 
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Get the email of the user
     * 
     * @return The email of the user
     */
    public String getEmail() {
        return email;
    }

    public UserState getUserState() {
        return this.userState;
    }

    /**
     * set the name of the user
     * used when the user modifies username
     *
     * @param username The name of the user
     */
    public void setUserName(String name) {
        this.username = name;
    }

    /**
     * change the password of the user
     * 
     * @param password The new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * set the name of the user
     * 
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the email of the user
     * 
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * set the state of the user
     * 
     * @param userState the new state of the user
     */
    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    /**
     * {{@inheritDoc}}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, this.id, this.username, this.password,
                this.name, this.email, this.isAdmin, this.userState.toString());
    }
}
