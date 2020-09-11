package com.example.android.learn_dagger.network.auth;

import com.example.android.learn_dagger.models.User;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {

    @GET("users/{id}")
    Flowable<User> getUser(@Path("id") int id);


}
