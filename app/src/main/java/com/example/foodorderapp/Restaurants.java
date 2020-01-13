package com.example.foodorderapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Restaurants {
    @SerializedName("data")
    ArrayList<Restaurants> datails;

    public ArrayList<Restaurants> getDatails() { return datails; }

    private String restaurant_id, restaurant_name, restaurant_location, restaurant_email, restaurant_phone, restaurant_type, response;
    private String [] restaurant_images = {};

    public String getResponse() {
        return response;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public String getRestaurant_location() {
        return restaurant_location;
    }

    public String getRestaurant_email() {
        return restaurant_email;
    }

    public String getRestaurant_phone() {
        return restaurant_phone;
    }

    public String getRestaurant_type() {
        return restaurant_type;
    }

    public String[] getRestaurant_images() {
        return restaurant_images;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }
}
