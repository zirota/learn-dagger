package com.example.android.learn_dagger.di;

import com.example.android.learn_dagger.di.auth.AuthModule;
import com.example.android.learn_dagger.di.auth.AuthViewModelsModule;
import com.example.android.learn_dagger.di.main.MainFragmentModuleBuilder;
import com.example.android.learn_dagger.di.main.MainModule;
import com.example.android.learn_dagger.di.main.MainViewModelsModule;
import com.example.android.learn_dagger.ui.auth.AuthActivity;
import com.example.android.learn_dagger.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

// Place for all activity dependencies to live
// Automatically creates the required activity that is to be provide by using @ContributesAndroid Injector
// Objects that are declared to be provided here can be injected to other classes
// Dagger only cares about the type and not the name of the variable
// Consider using @Named tag to differentiate between similar types
@Module
public abstract class ActivityModuleBuilder {

    // @ContributesAndroidInjector automatically creates a subcomponent
    // This specifies that AuthViewModels can only be used by this activity being provided
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity provideAuthActivity();

    @ContributesAndroidInjector(modules = {MainFragmentModuleBuilder.class, MainViewModelsModule.class,
            MainModule.class
    })
    abstract MainActivity provideMainActivity();


}
