package com.example.eventregistration.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Event implements Parcelable {

    @SerializedName("objects")
    private ArrayList<Objects> objects;

    public Event(ArrayList<Objects> objects) {
        this.objects = objects;
    }

    protected Event(Parcel in) {
        objects = in.createTypedArrayList(Objects.CREATOR);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public ArrayList<Objects> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Objects> objects) {
        this.objects = objects;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(objects);
    }
}
