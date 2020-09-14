package com.example.android.learn_dagger.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.SessionManager;
import com.example.android.learn_dagger.models.User;
import com.example.android.learn_dagger.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";
    private final AuthApi authApi;
    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthViewModel is working");

        if (this.authApi == null ) {
            Log.d(TAG, "AuthAPI IS NULL");
        } else {
            Log.d(TAG, "AuthAPI NOT NULL");
        }

    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "Authenticating with id, attempting to login");

        // Making the network request
        final LiveData<AuthResource<User>> source = queryUserId(userId);
        sessionManager.authenticateWithId(source);
    }

    // The LiveData object to be observed
    public LiveData<AuthResource<User>> observeUser() {
        return sessionManager.getAuthUser();
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(throwable -> {
                            User errorUser = new User();
                            errorUser.setId(-1);
                            return errorUser;
                        })
                        .map(
                                user -> {
                                    // Notify error
                                    if (user.getId() == -1) {
                                        return AuthResource.error("Could not authenticate ", (User) null);
                                    }
                                    // Maps a success response
                                    return AuthResource.authenticated(user);
                                }
                        ).subscribeOn(Schedulers.io())
        );
    }

    // private void testGetUser() {
    //     authApi.getUser(1).toObservable().subscribeOn(Schedulers.io()).subscribe(new Observer<User> () {
    //
    //         @Override
    //         public void onSubscribe(
    //                 @NonNull Disposable d
    //         ) {
    //
    //         }
    //
    //         @Override
    //         public void onNext(
    //                 @NonNull User user
    //         ) {
    //             Log.d(TAG, "Email " + user.getEmail());
    //         }
    //
    //         @Override
    //         public void onError(@NonNull Throwable e) {
    //             Log.e(TAG, "onError: " + e);
    //         }
    //
    //         @Override
    //         public void onComplete() {
    //
    //         }
    //     });
    // }
}
