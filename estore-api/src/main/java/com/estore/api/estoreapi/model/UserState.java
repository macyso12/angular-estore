package com.estore.api.estoreapi.model;

/**
 * emun type of user state
 * @author isTeamTwo
 */
public enum UserState {
    LOGGED_IN("logged in"),
    LOGGED_OUT("logged out"),
    USER_NOT_FOUND("user not found"),
    INCORRECT_PASSWORD("incorrect password");

    private String userState;

    UserState(String userState) {
        this.userState = userState;
    }

    public String toString() {
        return this.userState;
    }
}