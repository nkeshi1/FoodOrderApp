package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class food_list_display_frag extends Fragment {
    private static API_INTERFACE api_interface;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.food_list_display_frag,container, false);

        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        final Call<Foods> food_details = api_interface.food_details();
        food_details.enqueue(new Callback<Foods>() {
            @Override
            public void onResponse(Call<Foods> call, final Response<Foods> response) {
                final ListView foodList = view.findViewById(R.id.food_listview);
                final FoodAdapter adapter = new FoodAdapter(getActivity(), response.body().getFood_details());
                foodList.setAdapter(adapter);
                foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(getActivity(), food_item_selected.class).putExtra("food_selected", response.body().getFood_details().get(position).getFood_name()));
                    }
                });
            }

            @Override
            public void onFailure(Call<Foods> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
