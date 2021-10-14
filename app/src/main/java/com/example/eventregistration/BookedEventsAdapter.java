package com.example.eventregistration;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventregistration.model.Objects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BookedEventsAdapter extends RecyclerView.Adapter<BookedEventsAdapter.ViewHolder> {

    private ArrayList<Objects> events;

    public BookedEventsAdapter(ArrayList<Objects> events) {
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_event_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.eventName.setText(events.get(position).getName());
        holder.eventTime.setText(events.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout.LayoutParams params;
        public ConstraintLayout rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            params = new ConstraintLayout.LayoutParams(0, 0);
            rootView = itemView.findViewById(R.id.rootView);
        }

        TextView eventName = itemView.findViewById(R.id.event_name);
        TextView eventTime = itemView.findViewById(R.id.event_time);
        ImageView eventImage = itemView.findViewById(R.id.event_image);

    }

}

