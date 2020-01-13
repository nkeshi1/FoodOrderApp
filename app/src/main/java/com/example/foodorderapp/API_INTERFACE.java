package com.example.foodorderapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_INTERFACE{
    @POST("clientLogin.php")
    Call<Login_Register_Credentials> login_details(@Body Login_Register_Credentials credentials);

    @GET("restaurantDetails.php")
    Call<Restaurants> restaurant_details();

    @POST("saveMainDish.php")
    Call<OrderDetails> order_details(@Body OrderDetails orderDetails);

    @FormUrlEncoded
    @POST("deleteOrder.php")
    Call<OrderDetails> deleteOrder(@Field("orderid") String orderid);

    @GET("retrieveOrder.php")
    Call<OrderDetails> ordersRetrieved();

    @FormUrlEncoded
    @POST("saveComplementNames.php")
    Call<OrderDetails> complement_names(@Field("complements") String orderDetails,@Field("orderid") String orderid);

    @FormUrlEncoded
    @POST("saveComplementPrices.php")
    Call<OrderDetails> complement_prices(@Field("complements_prices") String orderDetails, @Field("orderid") String orderid);

    @FormUrlEncoded
    @POST("retrieveToCart.php")
    Call<SetComplements> order_info(@Field("order_id") String order_id, @Field("client_id") String client_id);

    @GET("foodDetails.php")
    Call<Foods> food_details();

    @GET("foodComplements.php")
    Call<Complement> food_complement();

    @POST("register_user.php")
    Call<Login_Register_Credentials> register_user(@Body Login_Register_Credentials register_credentials);
}
