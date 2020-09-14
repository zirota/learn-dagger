package com.example.android.learn_dagger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.learn_dagger.ui.auth.AuthActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribeObservers();
    }

    // This method will start observing the live data that exists in the ViewModel when the Activity is created
    // To tell this Activity what to observe, return the LiveData that exists in the ViewModel
    private void subscribeObservers() {
        sessionManager.getAuthUser().observe(this, userAuthResource -> {
            if (userAuthResource != null) {
                switch (userAuthResource.status) {
                    case LOADING:{
                        break;
                    }
                    case AUTHENTICATED:{
                        Log.d(TAG, "LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                        break;
                    }
                    case ERROR:{
                        Log.d(TAG, "ERROR");
                        break;
                    }
                    case NOT_AUTHENTICATED: {
                        navLoginScreen();
                        break;
                    }
                }
            }
        });
    }

    // Navigate to login activity and destroy the current activity
    private void navLoginScreen() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
    }

}
