package com.theironyard.CommandObjects;

/**
 * Created by darionmoore on 1/14/17.
 */
public class UserCommand {
    private String userName;
    private String password;

    public UserCommand() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
