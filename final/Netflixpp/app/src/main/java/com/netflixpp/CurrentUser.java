package com.netflixpp;

import android.app.Application;

public class CurrentUser extends Application {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        CurrentUser.currentUser = user;
    }

    public static String JsontoString(){
        return "ID: " + currentUser.getId() + "; username: " + currentUser.getUsername() + "; name: " + currentUser.getFullName() + "; isCreator: " + currentUser.isCreator();
    }
}
