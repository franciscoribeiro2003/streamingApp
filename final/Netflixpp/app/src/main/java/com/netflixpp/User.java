package com.netflixpp;

public class User {
    String id, fullName, username;
    boolean isCreator;

    public User(String id, String fullName, String username, String isCreator) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        if (isCreator.equals("1")) {
            this.isCreator = true;
        }
        else {
            this.isCreator = false;
        }
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreator(boolean creator) {
        isCreator = creator;
    }


    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public boolean isCreator() {return isCreator;}




}
