package com.example.android.learn_dagger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.learn_dagger.ui.auth.AuthResource;

public abstract class BaseResource<T> {

    @NonNull
    public T status;

    @Nullable
    public T data;

    @Nullable
    public String message;

    public static <T> AuthResource<T> authenticated (@Nullable T data) {
        return new AuthResource<>(AuthResource.AuthStatus.AUTHENTICATED, data, null);
    }

    public static <T> AuthResource<T> error(@NonNull String msg, @Nullable T data) {
        return new AuthResource<>(AuthResource.AuthStatus.ERROR, data, msg);
    }

    public static <T> AuthResource<T> loading(@Nullable T data) {
        return new AuthResource<>(AuthResource.AuthStatus.LOADING, data, null);
    }

    public static <T> AuthResource<T> logout () {
        return new AuthResource<>(AuthResource.AuthStatus.NOT_AUTHENTICATED, null, null);
    }

    public enum AuthStatus { AUTHENTICATED, ERROR, LOADING, NOT_AUTHENTICATED}
}
