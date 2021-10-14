package com.example.eventregistration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventregistration.model.Event;
import com.example.eventregistration.model.Objects;
import com.example.eventregistration.rest.ApiClient;
import com.example.eventregistration.rest.ApiInterface;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    TextView eventName, eventTime, eventLink;
    ImageView eventImage;
    Button bookEventBtn;
    Integer isClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventName = findViewById(R.id.event_detail_name);
        eventTime = findViewById(R.id.event_detail_time);
        eventLink = findViewById(R.id.event_detail_link);
        eventImage = findViewById(R.id.event_detail_image);
        bookEventBtn = findViewById(R.id.book_event_btn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Objects event = extras.getParcelable("EXTRA_EVENT");
        Integer bookedOrNot = extras.getInt("EXTRA_INT");
        String userEmail = extras.getString("USER_EMAIL");
        String userPhNo = extras.getString("USER_PHNO");
        //final Objects contests = getIntent().getParcelableExtra("EXTRA_CONTEST");
        //Log.i("ContestDetail", "contests.size event name inside ContestDetail is : " + contests.getEvent());

        eventName.setText(event.getName());
        eventLink.setText(Html.fromHtml("<a href=\"" + event.getLink() + "\">" + "Click here to know more" + "</a>"));
        eventLink.setClickable(true);
        eventLink.setMovementMethod(LinkMovementMethod.getInstance());

        eventTime.setText(event.getTime());

        if (bookedOrNot == 1) {
            bookEventBtn.setVisibility(View.GONE);
        } else {
            bookEventBtn.setVisibility(View.VISIBLE);
        }

        bookEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookedOrNot == 2 && isClicked == 0) {
                    bookEvent(userEmail, userPhNo, event);
                }
            }
        });
    }

    private void bookEvent(String email, String phNo, Objects event) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Event> call = apiService.bookEvent(email, phNo, event.getEventId());
        call.enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                ArrayList<Objects> events = new ArrayList<>();
                if (response.body() != null) {
                    int statusCode = response.code();
                    events = response.body().getObjects();
                } else {
                    events = null;
                }

                isClicked = 1;
                bookEventBtn.setEnabled(false);
                bookEventBtn.setBackgroundColor(Color.GRAY);
                bookEventBtn.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                bookEventBtn.setText("Booked");
                Toast.makeText(getApplicationContext(), "Event is successfully booked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Check your internet connection or try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }
}