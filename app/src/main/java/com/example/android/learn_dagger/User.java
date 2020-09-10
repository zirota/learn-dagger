package com.example.android.learn_dagger;

import javax.inject.Singleton;

// @Singleton ensures only one instance of this object is created
@Singleton
public class User {
    private int id;
    private String username;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
