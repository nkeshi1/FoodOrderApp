package com.example.foodorderapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class restaurant_details_frag extends Fragment {
    public static int currentRes;
    public static API_INTERFACE api_interface;
    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.restuarant_details, container, false);

        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        final Call<Restaurants> res_details = api_interface.restaurant_details();
        res_details.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                TextView res_name = view.findViewById(R.id.restaurant_name);
                res_name.setText(""+response.body().getDatails().get(currentRes).getRestaurant_name());

                TextView res_working_hrs = view.findViewById(R.id.working_hrs);

                TextView res_type = view.findViewById(R.id.restaurant_type);
                res_type.setText(""+response.body().getDatails().get(currentRes).getRestaurant_type());

                TextView res_mail = view.findViewById(R.id.email);
                res_mail.setText(""+response.body().getDatails().get(currentRes).getRestaurant_email());
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
//                Toast.makeText(t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
}
