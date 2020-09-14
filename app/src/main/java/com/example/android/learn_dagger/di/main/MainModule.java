package com.example.android.learn_dagger.di.main;

import com.example.android.learn_dagger.network.main.MainApi;
import com.example.android.learn_dagger.ui.main.posts.PostRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @Provides
    static MainApi provideMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }

    @Provides
    static PostRecyclerAdapter provideRecyclerAdapter() {
        return new PostRecyclerAdapter();
    }
}
