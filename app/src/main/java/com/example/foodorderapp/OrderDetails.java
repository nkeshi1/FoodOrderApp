package com.example.foodorderapp;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class OrderDetails {
    private String client_id, main_dish, comp_names, comp_prices, order_id, response;
    private int main_dish_price,  total_cost;
    private int order_copies = 1;
    private static int index = -1, cart_cost;

    @SerializedName("order")
    ArrayList<OrderDetails> orderDetails;

    public static int getCart_cost() {
        return cart_cost;
    }

    public static void setCart_cost(int cart_cost) {
        OrderDetails.cart_cost = cart_cost;
    }

    public String getComp_prices() { return comp_prices; }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public static ArrayList<String> complements = new ArrayList<>();
    public static ArrayList<String> complements_prices = new ArrayList<>();

    public String getComp_names() { return comp_names;}

    public static ArrayList<String> getComplements() { return complements; }

    public static void setComplements(String complements) {
        if(OrderDetails.complements.contains(complements)){
            index = OrderDetails.complements.indexOf(complements);
            OrderDetails.complements.remove(index);
            OrderDetails.complements.add(index, complements);
        }else{
            OrderDetails.complements.add(complements);
        }
    }

    public static ArrayList<String> getComplements_prices() { return complements_prices; }

    public static void setComplements_prices(String complements_prices) {
        if(OrderDetails.complements_prices.size() != OrderDetails.complements.size()){
            OrderDetails.complements_prices.add(complements_prices);
        }else{
            OrderDetails.complements_prices.remove(index);
            OrderDetails.complements_prices.add(index, complements_prices);
        }
    }

    public int getTotal_cost() { return total_cost; }

    public void setTotal_cost(int total_cost) { this.total_cost = total_cost; }

    public String getResponse() { return response; }

    public String getClient_id() { return client_id; }

    public void setClient_id(String client_id) { this.client_id = client_id; }

    public int getOrder_copies() { return order_copies; }

    public void setOrder_copies(int order_copies) { this.order_copies = order_copies; }

    public void addOrderCopy(){
        this.order_copies += 1;
    }

    public void removeOrderCopy(){
        if(this.order_copies > 1){
            this.order_copies -= 1;
        }
    }

    public String getOrder_id() { return order_id; }

    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getMain_dish() { return main_dish; }

    public void setMain_dish(String main_dish) { this.main_dish = main_dish; }

    public int getMain_dish_price() { return main_dish_price; }

    public void setMain_dish_price(int main_dish_price) { this.main_dish_price = main_dish_price; }
}
