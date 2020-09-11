package com.example.android.learn_dagger.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.models.User;
import com.example.android.learn_dagger.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private MediatorLiveData<AuthResource<User>> authUser = new MediatorLiveData<>();

    private final AuthApi authApi;

    @Inject
    public AuthViewModel(AuthApi authApi) {
        this.authApi = authApi;
        Log.d(TAG, "AuthViewModel is working");

        if (this.authApi == null ) {
            Log.d(TAG, "AuthAPI IS NULL");
        } else {
            Log.d(TAG, "AuthAPI NOT NULL");
        }

    }

    public void authenticateWithId(int userId) {
        authUser.setValue(AuthResource.loading((User) null));

        final LiveData<AuthResource<User>> source = LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId).onErrorReturn(handleError()).map(
                        user -> {
                            if (user.getId() == -1) {
                                return AuthResource.error("Could not authenticate", (User) null);
                            }
                            return null;
                        }
                ).subscribeOn(Schedulers.io())
        );


        authUser.addSource(source, user -> {
            authUser.setValue(user);

            // Clean up after no need listen
            authUser.removeSource(source);
        });
    }

    // The LiveData object to be observed
    public LiveData<AuthResource<User>> observeUser() {
        return authUser;
    }

    private Function<Throwable, User> handleError() {
        return throwable -> {
            User errorUser = new User();
            errorUser.setId(-1);
            return errorUser;
        };
    }



    private void testGetUser() {
        authApi.getUser(1).toObservable().subscribeOn(Schedulers.io()).subscribe(new Observer<User> () {

            @Override
            public void onSubscribe(
                    @NonNull Disposable d
            ) {

            }

            @Override
            public void onNext(
                    @NonNull User user
            ) {
                Log.d(TAG, "Email " + user.getEmail());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {

            }
        });
    }
}
