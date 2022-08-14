export class User {
    id: number;
    username: string;
    password: string;
    name: string;
    email: string;
    isAdmin: boolean;
    userState: UserState;

    constructor(id: number, username: string, password: string,
        name: string, email: string, isAdmin: boolean, userState: UserState) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
        this.userState = userState;
    }
}

export enum UserState {
    LOGGED_IN = "LOGGED_IN",
    LOGGED_OUT = "LOGGED_OUT",
    USER_NOT_FOUND = "USER_NOT_FOUND",
    INCORRECT_PASSWORD = "INCORRECT_PASSWORD"
}