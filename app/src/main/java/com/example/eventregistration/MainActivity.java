package com.example.eventregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.eventregistration.utils.OverlayFrame;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    OverlayFrame overlayFrame;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    CoordinatorLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userEmail = getIntent().getExtras().getString("emailArg");
        String userPass = getIntent().getExtras().getString("phNoArg");

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        progressBar = findViewById(R.id.progress_bar_overlay);
        rootView = findViewById(R.id.root_activty_main);
        overlayFrame = findViewById(R.id.overlay_frame);
        overlayFrame.displayOverlay(true);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}