package com.example.eventregistration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eventregistration.model.Objects;
import com.example.eventregistration.utils.ItemClickSupport;
import com.example.eventregistration.utils.OverlayFrame;

import java.util.ArrayList;

public class BookedFragment extends Fragment {

    private static final String BOOKED_PARAM_1 = "bookedUserEmail";
    private static final String BOOKED_PARAM_2 = "bookedUserPhone";
    private static final String BOOKED_PARAM_3 = "bookedEvents";

    private String userEmail;
    private String userPhNo;
    ArrayList<Objects> bookedEvents = new ArrayList<Objects>();
    RecyclerView bookedRecyclerView;
    TextView emptyBooked, emptyNetworkIssueBooked;
    OverlayFrame overlayFrame;

    public BookedFragment() {
        // Required empty public constructor
    }

    public static BookedFragment newInstance(String email, String phNo, ArrayList<Objects> events) {
        BookedFragment fragment = new BookedFragment();
        Bundle args = new Bundle();
        args.putString(BOOKED_PARAM_1, email);
        args.putString(BOOKED_PARAM_2, phNo);
        args.putParcelableArrayList(BOOKED_PARAM_3, events);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userEmail = getArguments().getString(BOOKED_PARAM_1);
            userPhNo = getArguments().getString(BOOKED_PARAM_2);
            bookedEvents = getArguments().getParcelableArrayList(BOOKED_PARAM_3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overlayFrame = getActivity().findViewById(R.id.overlay_frame);
        //overlayFrame.displayOverlay(true);
        bookedRecyclerView = view.findViewById(R.id.booked_events_rv);
        emptyBooked = view.findViewById(R.id.empty_booked_events_tv);
        emptyNetworkIssueBooked = view.findViewById(R.id.empty_network_issue_booked_events_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        bookedRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.i("UpcomingAllFragment", "UpcomingAllFragment contestsAll : " + contestsAll.size());
        if (bookedEvents != null && bookedEvents.size() != 0) {
            Log.i("BookedFragment", "Inside not empty bookedEvents");
            bookedRecyclerView.setAdapter(new BookedEventsAdapter(bookedEvents));
            //overlayFrame.displayOverlay(false);
        } else {
            emptyBooked.setVisibility(View.VISIBLE);
            //emptyNetworkIssueUpcomingAll.setVisibility(View.VISIBLE);
            //Log.i("UpcomingAllFragment","The visibility is set for the emtpy placeholder text views ");
            //overlayFrame.displayOverlay(false);
        }
        //overlayFrame.displayOverlay(false);

        ItemClickSupport.addTo(bookedRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i("BookedEventsFragment", "position of click : " + position);
                //Log.i("UpcomingAllFragment", "on click contestsAll size is : " + contestsAll.size());
                showSelectedContestDetail(bookedEvents.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects bookedEvent) {
        //Log.i("UpcomingAllFragment", "on click contestsAll.getpos platform name is : " + contestsAll.getResource().getName());
        Intent intentEventDetail = new Intent(getActivity(), EventDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_EVENT", bookedEvent);
        extras.putInt("EXTRA_INT", 1);
        extras.putString("USER_EMAIL", userEmail);
        extras.putString("USER_PHNO", userPhNo);
        intentEventDetail.putExtras(extras);
        startActivity(intentEventDetail);
    }
}