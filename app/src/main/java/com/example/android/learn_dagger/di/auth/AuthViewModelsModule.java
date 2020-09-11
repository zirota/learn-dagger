package com.example.android.learn_dagger.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.di.ViewModelKey;
import com.example.android.learn_dagger.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

// All other view models dependant this view model can be added here
@Module
public abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
