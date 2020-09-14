package com.example.android.learn_dagger.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.example.android.learn_dagger.R;
import com.example.android.learn_dagger.databinding.ActivityAuthBinding;
import com.example.android.learn_dagger.di.ViewModelProviderFactory;
import com.example.android.learn_dagger.ui.main.MainActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";

    @Inject ViewModelProviderFactory factory;

    @Inject Drawable logo;

    @Inject RequestManager requestManager;

    private AuthViewModel viewModel;

    private EditText userId;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();

        // Binding has faster performance than findViewById. Binding will be auto generated
        ActivityAuthBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        setLogo(binding);


        userId = binding.username;
        progressBar = binding.progressBar;
        binding.nextButton.setOnClickListener(this);
        subscribeObservers();
    }

    public void subscribeObservers() {
      viewModel.observeUser().observe(this, userAuthResource -> {
          if (userAuthResource != null) {
              switch (userAuthResource.status) {
                  case LOADING:{
                      showProgressBar(true);
                      break;
                  }
                  case AUTHENTICATED:{
                      showProgressBar(false);
                      onLoginSuccess();
                      Log.d(TAG, "LOGIN SUCCESS: " + userAuthResource.data.getEmail());
                      break;
                  }
                  case ERROR:{
                      showProgressBar(false);
                      Toast.makeText(AuthActivity.this, userAuthResource.message + "did you enter a number between 1 to 10?", Toast.LENGTH_SHORT).show();
                      break;
                  }
                  case NOT_AUTHENTICATED: {
                      showProgressBar(false);
                      break;
                  }
              }
          }
      });
    }

    private void onLoginSuccess() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void setLogo(ActivityAuthBinding binding) {
        requestManager.load(logo).into(binding.loginLogo);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button: {
                attemptLogin();
                break;
            }
        }
    }

    private void attemptLogin() {
        String userId = this.userId.getText().toString();
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userId));
    }
}
