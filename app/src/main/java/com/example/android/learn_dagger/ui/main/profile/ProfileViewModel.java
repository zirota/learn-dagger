package com.example.android.learn_dagger.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.SessionManager;
import com.example.android.learn_dagger.models.User;
import com.example.android.learn_dagger.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {
    private static final String TAG = "ProfileViewModel";

    private final SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileViewModel: Is ready");
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }
}
