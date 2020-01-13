package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class FoodAdapter extends  ArrayAdapter<Foods>{

    public FoodAdapter(Context context, List<Foods> food_details) {
        super(context, 0, food_details);
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        Foods food_details = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_item_available, parent, false);
        TextView food_name = convertView.findViewById(R.id.food_name);
        food_name.setText(""+food_details.getFood_name());
        return convertView;
    }
}

