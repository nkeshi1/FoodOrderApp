package com.example.foodorderapp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Complement implements Serializable {
    private String food_id, complement_id, complement_name, price_max, price_min, response;
    private int mPrice;
    private int mainDishPrice = 5;
    public static int totalPrice;

    public Complement(){}
    public Complement(int mainDishPrice){}
    public Complement(String complement_name){
        this.complement_name = complement_name;
        this.mPrice = 0;
    }

    public int getTotalPrice(){
        return totalPrice + mainDishPrice;
    }

    public int getmPrice() {
        return mPrice;
    }

    public void setMainDishPrice(int mainDishPrice){
       this.mainDishPrice = mainDishPrice;
    }

    @SerializedName("data")
    ArrayList<Complement> complements_details;

    public void setComplement_name(String complement_name) { this.complement_name = complement_name; }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public void addToQuantity(){
        this.mPrice += 1;
        this.totalPrice += 1;
    }

    public void removeFromQuantity(){
        if(this.mPrice > 0){
            this.mPrice -= 1;
            this.totalPrice -= 1;
        }
    }

    public ArrayList<Complement> getComplements_details() { return complements_details; }

    public String getFood_id() {
        return food_id;
    }

    public String getResponse() { return response; }

    public String getComplement_id() {
        return complement_id;
    }

    public String getComplement_name() {
        return complement_name;
    }

    public String getPrice_max() {
        return price_max;
    }

    public String getPrice_min() {
        return price_min;
    }
}
