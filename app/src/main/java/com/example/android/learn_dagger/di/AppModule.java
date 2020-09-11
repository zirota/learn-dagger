package com.example.android.learn_dagger.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.android.learn_dagger.R;
import com.example.android.learn_dagger.utils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class AppModule {

    @Provides
    static String someString() {
        return "this is the test string";
    }

    // This will return false as the application object is passed from the Builder
    // @Provides
    // static boolean getApp(Application application) {
    //     return application == null;
    // }

    // This will return 1 because we are already providing a type String as seen above
    // @Provides
    // static int someInt(String string) {
    //     if (string.equalsIgnoreCase("this is the test string")) {
    //         return 1;
    //     }
    //     return 0;
    // }

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(
                        RxJava3CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static RequestOptions provideRequestOptions() {
        return RequestOptions.placeholderOf(R.color.design_default_color_background)
                .error(R.color.design_default_color_error);
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(
            Application application, RequestOptions requestOptions
    ) {
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable provideAppDrawable(Application application) {
        return ContextCompat.getDrawable(application, R.drawable.baseline_fireplace_24);
    }
}
