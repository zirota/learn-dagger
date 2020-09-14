package com.example.android.learn_dagger.di.main;

import androidx.lifecycle.ViewModel;

import com.example.android.learn_dagger.di.ViewModelKey;
import com.example.android.learn_dagger.ui.main.posts.PostViewModel;
import com.example.android.learn_dagger.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel.class)
    public abstract ViewModel bindPostViewModel(PostViewModel viewModel);
}
