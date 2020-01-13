package com.example.foodorderapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Foods {
    private String food_name;
    private String restaurant_id;
    private String food_id;
    private String order_status;
    private String price_range;
    private String food_availability;
    private String response;

    @SerializedName("body")
    ArrayList<Foods> food_details;

    public String getResponse() {
        return response;
    }

    public String getOrder_status() { return order_status; }

    public ArrayList<Foods> getFood_details() { return food_details; }

    public String getFood_name() {
        return food_name;
    }

    public String getPrice_range() {
        return price_range;
    }

    public String getFood_availability() {
        return food_availability;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public String isFood_availability() {
        return food_availability;
    }


}
