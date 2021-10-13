package com.example.eventregistration.rest;

import com.example.eventregistration.model.Event;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/")
    Call<Event> bookEvent(@Query("email") String email, @Query("phone_number") String phone_number, @Query("eventId") String eventId);

    @GET("/")
    Call<Event> getBookedEvents(@Query("email") String email, @Query("phone_number") String phone_number);

    @GET("/")
    Call<Event> getUnbookedEvents(@Query("email") String email, @Query("phone_number") String phone_number);

}
