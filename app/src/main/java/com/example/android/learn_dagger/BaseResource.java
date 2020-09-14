package com.example.android.learn_dagger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// A typical structure of a network resource according to Android best practices
public class BaseResource<T> {

    @NonNull
    public Status status;


    @Nullable
    public T data;

    @Nullable
    public String message;

    public BaseResource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> BaseResource<T> success(@Nullable T data) {
        return new BaseResource<>(Status.SUCCESS, data, null);
    }

    public static <T> BaseResource<T> error(@NonNull String msg, @Nullable T data) {
        return new BaseResource<>(Status.ERROR, data, msg);
    }

    public static <T> BaseResource<T> loading(@Nullable T data) {
        return new BaseResource<>(Status.LOADING, data, null);
    }

    public enum Status { SUCCESS, ERROR, LOADING }
}
