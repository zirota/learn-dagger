package com.example.android.learn_dagger.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.android.learn_dagger.BaseActivity;
import com.example.android.learn_dagger.R;
import com.example.android.learn_dagger.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
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

            // Allow menu drawer to be close via tapping the icon
            case (android.R.id.home): {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Using Navigation eliminates the need of having fragment transactions
        // Fragment replacements are handled using the controller with navigate
        // The available fragments to be navigate to are declared within the navigation graph
        switch (item.getItemId()) {
            case R.id.nav_profile: {
                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true)
                        .build();

                Navigation.findNavController(this, R.id.nav_host_fragment_container)
                        .navigate(R.id.profileScreen, null, navOptions);
                break;
            }
            case R.id.nav_posts: {

                // Prevents add to backstack repeatedly
                if (isValidDestination(R.id.postScreen)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment_container)
                            .navigate(R.id.postScreen);
                }
                break;
            }
        }
        // Highlights the clicked item
        item.setChecked(true);

        // Controls the side where drawer appears
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // Configure how the back navigation works
    // Tells the app to reference the navigation controller when navigating up
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(
                Navigation.findNavController(this, R.id.nav_host_fragment_container), binding
                        .drawerLayout);
    }

    private boolean isValidDestination(int destination) {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment_container)
                .getCurrentDestination()
                .getId();
    }

    private void init() {
        // NavController navController = Navigation.findNavController(
        //         this, R.id.nav_host_fragment_container);

        // Current workaround to find NavController for FragmentViewContainer layout
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                        R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, binding.drawerLayout);
        NavigationUI.setupWithNavController(binding.navView, navController);
        binding.navView.setNavigationItemSelectedListener(this);
    }
}
