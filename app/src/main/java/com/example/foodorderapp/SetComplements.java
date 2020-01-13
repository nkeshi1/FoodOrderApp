package com.example.foodorderapp;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SetComplements {
    private String food_id, complement_id, complement_prices, main_dish ,complement_name, order_id, client_id, response;
    private int mPrice, total_cost, mainDishPrice;
    public static int totalPrice;

//    String[] complement_names = complement_name.trim().substring(1,complement_name.length()-1).split(",");

//    public String[] getComplement_names() { return complement_names; }

    public String getResponse() {
        return response;
    }

    public String getMain_dish() {
        return main_dish;
    }

    public int getMainDishPrice() {
        return mainDishPrice;
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

    @SerializedName("order")
    ArrayList<SetComplements> complements_details;

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

    public ArrayList<SetComplements> getComplements_details() { return complements_details; }

    public String getFood_id() {
        return food_id;
    }

    public String getComplement_id() {
        return complement_id;
    }

    public String getComplement_name() {
        return complement_name;
    }
}
