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


public class EventsFragment extends Fragment {

    private static final String UNBOOKED_PARAM_1 = "unBookedUserEmail";
    private static final String UNBOOKED_PARAM_2 = "unBookedUserPhone";
    private static final String UNBOOKED_PARAM_3 = "unBookedEvents";

    private String userEmail;
    private String userPhNo;
    ArrayList<Objects> unBookedEvents = new ArrayList<Objects>();
    RecyclerView unBookedRecyclerView;
    TextView emptyUnBooked, emptyNetworkIssueUnBooked;
    OverlayFrame overlayFrame;

    public EventsFragment() {
        // Required empty public constructor
    }

    public static EventsFragment newInstance(String email, String phNo, ArrayList<Objects> events) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(UNBOOKED_PARAM_1, email);
        args.putString(UNBOOKED_PARAM_2, phNo);
        args.putParcelableArrayList(UNBOOKED_PARAM_3, events);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userEmail = getArguments().getString(UNBOOKED_PARAM_1);
            userPhNo = getArguments().getString(UNBOOKED_PARAM_2);
            unBookedEvents = getArguments().getParcelableArrayList(UNBOOKED_PARAM_3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        overlayFrame = getActivity().findViewById(R.id.overlay_frame);
        //overlayFrame.displayOverlay(true);
        unBookedRecyclerView = view.findViewById(R.id.events_rv);
        emptyUnBooked = view.findViewById(R.id.empty_events_tv);
        emptyNetworkIssueUnBooked = view.findViewById(R.id.empty_network_issue_events_tv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        unBookedRecyclerView.setLayoutManager(linearLayoutManager);
        //Log.i("UpcomingAllFragment", "UpcomingAllFragment contestsAll : " + contestsAll.size());
        if (unBookedEvents != null && unBookedEvents.size() != 0) {
            Log.i("BookedFragment", "Inside not empty bookedEvents");
            unBookedRecyclerView.setAdapter(new EventsAdapter(unBookedEvents));
            //overlayFrame.displayOverlay(false);
        } else {
            emptyUnBooked.setVisibility(View.VISIBLE);
            //emptyNetworkIssueUpcomingAll.setVisibility(View.VISIBLE);
            //Log.i("UpcomingAllFragment","The visibility is set for the emtpy placeholder text views ");
            //overlayFrame.displayOverlay(false);
        }
        //overlayFrame.displayOverlay(false);

        ItemClickSupport.addTo(unBookedRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Log.i("UnBookedEventsFragment", "position of click : " + position);
                //Log.i("UpcomingAllFragment", "on click contestsAll size is : " + contestsAll.size());
                showSelectedContestDetail(unBookedEvents.get(position));
            }
        });

    }

    private void showSelectedContestDetail(Objects unBookedEvent) {
        //Log.i("UpcomingAllFragment", "on click contestsAll.getpos platform name is : " + contestsAll.getResource().getName());
        Intent intentEventDetail = new Intent(getActivity(), EventDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable("EXTRA_EVENT", unBookedEvent);
        extras.putInt("EXTRA_INT", 2);
        intentEventDetail.putExtras(extras);
        startActivity(intentEventDetail);
    }
}
