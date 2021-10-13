package com.example.eventregistration.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Objects implements Parcelable {

    @SerializedName("name")
    private String name;
    @SerializedName("time")
    private String time;
    @SerializedName("place")
    private String place;
    @SerializedName("eventId")
    private String eventId;
    @SerializedName("link")
    private String link;

    public Objects(String name, String time, String place, String eventId, String link) {
        this.name = name;
        this.time = time;
        this.place = place;
        this.eventId = eventId;
        this.link = link;
    }

    protected Objects(Parcel in) {
        name = in.readString();
        time = in.readString();
        place = in.readString();
        eventId = in.readString();
        link = in.readString();
    }

    public static final Creator<Objects> CREATOR = new Creator<Objects>() {
        @Override
        public Objects createFromParcel(Parcel in) {
            return new Objects(in);
        }

        @Override
        public Objects[] newArray(int size) {
            return new Objects[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(time);
        parcel.writeString(place);
        parcel.writeString(eventId);
        parcel.writeString(link);
    }
}
