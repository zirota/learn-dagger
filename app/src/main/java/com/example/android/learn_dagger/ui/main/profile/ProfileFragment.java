package com.example.android.learn_dagger.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.learn_dagger.databinding.FragmentProfileBinding;
import com.example.android.learn_dagger.di.ViewModelProviderFactory;
import com.example.android.learn_dagger.models.User;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class ProfileFragment extends DaggerFragment {
    private static final String TAG = "ProfileFragment";

    @Inject ViewModelProviderFactory factory;

    private FragmentProfileBinding binding;

    private ProfileViewModel viewModel;
    
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
        subscribeObservers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Fragments are no different, data must be observed to ensure that changes to data is reacted
    // to accordingly
    public void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), userAuthResource -> {
            if (userAuthResource != null && userAuthResource.data != null) {
                switch (userAuthResource.status) {
                    case AUTHENTICATED: {
                        setUserDetails(userAuthResource.data);
                        break;
                    }
                    case ERROR: {
                        setErrorDetails(userAuthResource.message);
                        break;
                    }
                }
            }
        });
    }

    private void setErrorDetails(String message) {
        binding.email.setText(message);
        binding.username.setText("Error");
    }

    private void setUserDetails(User user) {
        binding.email.setText(user.getEmail());
        binding.username.setText(user.getUsername());
    }
}
