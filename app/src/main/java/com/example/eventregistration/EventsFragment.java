package com.example.eventregistration;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eventregistration.model.Objects;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {

    private static final String UNBOOKED_PARAM_1 = "unBookedUserEmail";
    private static final String UNBOOKED_PARAM_2 = "unBookedUserPhone";
    private static final String UNBOOKED_PARAM_3 = "unBookedEvents";

    private String mParam1;
    private String mParam2;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(String userEmail, String userPhNo, ArrayList<Objects> unBookedEvents) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(UNBOOKED_PARAM_1, userEmail);
        args.putString(UNBOOKED_PARAM_2, userPhNo);
        args.putParcelableArrayList(UNBOOKED_PARAM_3, unBookedEvents);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(UNBOOKED_PARAM_1);
            mParam2 = getArguments().getString(UNBOOKED_PARAM_2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }
}