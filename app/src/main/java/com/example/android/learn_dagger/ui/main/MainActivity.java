package com.example.android.learn_dagger.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.android.learn_dagger.BaseActivity;
import com.example.android.learn_dagger.R;
import com.example.android.learn_dagger.databinding.ActivityMainBinding;
import com.example.android.learn_dagger.ui.main.profile.ProfileFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);
        // Toast.makeText(this, "MainActivity", Toast.LENGTH_SHORT).show();

        testFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.logout): {
               sessionManager.logout();
               return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void testFragment() {

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
    }
}
