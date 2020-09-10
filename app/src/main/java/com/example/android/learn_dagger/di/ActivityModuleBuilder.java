package com.example.android.learn_dagger.di;

import com.example.android.learn_dagger.AuthActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// Place for all activity dependencies to live
// Automatically creates the required activity that is to be provide by using @ContributesAndroid Injector
// Objects that are declared to be provided here can be injected to other classes
// Dagger only cares about the type and not the name of the variable
// Consider using @Named tag to differentiate between similar types
@Module
public abstract class ActivityModuleBuilder {

    @ContributesAndroidInjector
    abstract AuthActivity provideAuthActivity();


}
