package com.example.android.learn_dagger.di.main;

import com.example.android.learn_dagger.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentModuleBuilder {

    @ContributesAndroidInjector
    abstract ProfileFragment provideProfileFragment();
}
