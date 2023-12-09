package com.netflixpp;

public class User {
    String id, fullName, username;
    boolean isCreator;

    public User(String id, String fullName, String username, String isCreator) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.isCreator = Boolean.parseBoolean(isCreator);
    }


}
