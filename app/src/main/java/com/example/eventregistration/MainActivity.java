package com.example.eventregistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.eventregistration.model.Event;
import com.example.eventregistration.model.Objects;
import com.example.eventregistration.rest.ApiClient;
import com.example.eventregistration.rest.ApiInterface;
import com.example.eventregistration.utils.OverlayFrame;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    OverlayFrame overlayFrame;
    NavController navController;
    BottomNavigationView bottomNavigationView;
    ProgressBar progressBar;
    CoordinatorLayout rootView;
    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String userEmail = getIntent().getExtras().getString("emailArg");
        String userPhNo = getIntent().getExtras().getString("phNoArg");

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

        fetchData(userEmail, userPhNo);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    private void fetchData(String userEmail, String userPhNo) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Event> call = apiService.getBookedEvents(userEmail, userPhNo);

        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                ArrayList<Objects> bookedEvents = new ArrayList<>();
                if (response.body() != null) {
                    int statusCode = response.code();
                    bookedEvents = response.body().getObjects();
                    Log.i("MainAcitivity", "Not Empty Booked Events");
                } else {
                    bookedEvents = null;
                    Log.i("MainAcitivity", "Empty Booked Events");
                }

                fetchUnBookedEvents(userEmail, userPhNo, bookedEvents);
                //Log.i(TAG,"The size of contestsAll is "+contestsAll.size());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Snackbar snackbar = Snackbar.make(rootView, R.string.no_internet, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(16);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUnBookedEvents(String userEmail, String userPhNo, ArrayList<Objects> bookedEvents) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Event> call = apiService.getUnbookedEvents(userEmail, userPhNo);
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                ArrayList<Objects> unBookedEvents = new ArrayList<>();
                if (response.body() != null) {
                    int statusCode = response.code();
                    unBookedEvents = response.body().getObjects();
                } else {
                    unBookedEvents = null;
                }

                sendData(userEmail, userPhNo, bookedEvents, unBookedEvents);
                //Log.i(TAG,"The size of contestsAll is "+contestsAll.size());
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Snackbar snackbar = Snackbar.make(rootView, R.string.no_internet, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                snackbar.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                View view = snackbar.getView();
                TextView tv = view.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(16);
                snackbar.show();
                //Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendData(String userEmail, String userPhNo, ArrayList<Objects> bookedEvents, ArrayList<Objects> unBookedEvents) {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_booked:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        BookedFragment bookedFragment = BookedFragment.newInstance(userEmail, userPhNo, bookedEvents);
                        ft.replace(R.id.nav_host_fragment_container, bookedFragment);
                        ft.commit();
                        break;
                    case R.id.nav_events:
                        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                        EventsFragment eventsFragment = EventsFragment.newInstance(userEmail, userPhNo, unBookedEvents);
                        ft2.replace(R.id.nav_host_fragment_container, eventsFragment);
                        ft2.commit();
                        break;
                    case R.id.nav_about:
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction().replace(R.id.nav_host_fragment_container, new AboutFragment()).commit();
                        break;
                }
                return true;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.nav_booked) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    BookedFragment bookedFragment = BookedFragment.newInstance(userEmail, userPhNo, bookedEvents);
                    ft.replace(R.id.nav_host_fragment_container, bookedFragment);
                    ft.commit();
                } else if (destination.getId() == R.id.nav_events) {
                    FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                    EventsFragment eventsFragment = EventsFragment.newInstance(userEmail, userPhNo, unBookedEvents);
                    ft2.replace(R.id.nav_host_fragment_container, eventsFragment);
                    ft2.commit();
                } else {
                    FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                    AboutFragment aboutFragment = new AboutFragment();
                    ft3.replace(R.id.nav_host_fragment_container, aboutFragment);
                    ft3.commit();
                }
            }
        });

        overlayFrame.displayOverlay(false);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

    }
}