package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class CartOrdersAdapter extends ArrayAdapter<SetComplements> {
    private TextView price, currentFoodItem;
    private Button reduce_priceBtn, increase_priceBtn;

    SetComplements food_complement;
    public CartOrdersAdapter(Context context, List<SetComplements> complements) {
        super(context, 0, complements);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        food_complement = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.complement_layout, parent, false);

        String[] complement_names = food_complement.getComplement_name().trim().substring(1,food_complement.getComplement_name().length()-1).split(",");
        //for (String complement_name:complement_names) {
            currentFoodItem = convertView.findViewById(R.id.compliment_name);
        for (String food:complement_names) {
            currentFoodItem.setText(food);
        }
            reduce_priceBtn = convertView.findViewById(R.id.reduce_comp_price);
            reduce_priceBtn.setFocusable(false);

            price = convertView.findViewById(R.id.complement_price);
            increase_priceBtn = convertView.findViewById(R.id.increase_comp_price);
            increase_priceBtn.setFocusable(false);
            price.setText("" + food_complement.getmPrice());
        //}
        //Configuring the increase button
        reduce_priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                food_item_selected.isSaved = false;
                food_complement.removeFromQuantity();
//                OrderDetails.setComplements(food_complement.getComplement_name());
//                OrderDetails.setComplements_prices(""+food_complement.getmPrice());
                notifyDataSetChanged();
            }
        });

        increase_priceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
                food_item_selected.isSaved = false;
                food_complement.addToQuantity();
                //OrderDetails.setComplements(food_complement.getComplement_names());
                //OrderDetails.setComplements_prices(""+food_complement.getmPrice());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}

