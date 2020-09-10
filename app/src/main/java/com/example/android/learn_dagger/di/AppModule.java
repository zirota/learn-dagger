package com.example.android.learn_dagger.di;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class AppModule {

    @Provides
    static  String someString() {
        return "this is the test string";
    }

    // This will return false as the application object is passed from the Builder
    @Provides
    static boolean getApp(Application application) {
        return application == null;
    }

    // This will return 1 because we are already providing a type String as seen above
    @Provides
    static int someInt(String string) {
        if (string.equalsIgnoreCase("this is the test string")) {
            return 1;
        }
        return 0;
    }
}
