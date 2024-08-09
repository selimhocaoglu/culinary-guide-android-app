package com.selimhocaoglu.culinaryguide;

import com.selimhocaoglu.culinaryguide.model.User;

public class UserSessionManager {

    private static UserSessionManager instance;
    private User currentUser;

    private UserSessionManager() {

    }

    public static UserSessionManager getInstance(){
        if(instance == null){
            instance = new UserSessionManager();
        }
        return instance;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public Long getCurrentUserId(){
        return currentUser != null ? currentUser.getId() : null;
    }
}
