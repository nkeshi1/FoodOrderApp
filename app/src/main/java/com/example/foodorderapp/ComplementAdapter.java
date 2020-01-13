package com.example.foodorderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class ComplementAdapter extends ArrayAdapter<Complement>{
        private TextView price, currentFoodItem;
        private Button reduce_priceBtn, increase_priceBtn;

    public ComplementAdapter(Context context, List<Complement> complements) {
        super(context, 0, complements);
    }

    public ComplementAdapter(Context context) {
        super(context, 0);
    }

    @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Complement food_complement = getItem(position);
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.complement_layout, parent, false);
            currentFoodItem = convertView.findViewById(R.id.compliment_name);
            currentFoodItem.setText(food_complement.getComplement_name());
            reduce_priceBtn = convertView.findViewById(R.id.reduce_comp_price);
            reduce_priceBtn.setFocusable(false);

            price = convertView.findViewById(R.id.complement_price);
            increase_priceBtn = convertView.findViewById(R.id.increase_comp_price);
            increase_priceBtn.setFocusable(false);
            price.setText(""+food_complement.getmPrice());

            //Configuring the increase button
            reduce_priceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                    food_item_selected.isSaved = false;
                    food_complement.removeFromQuantity();
                    OrderDetails.setComplements(food_complement.getComplement_name());
                    OrderDetails.setComplements_prices(""+food_complement.getmPrice());
                    notifyDataSetChanged();
                }
            });

            increase_priceBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyDataSetChanged();
                    food_item_selected.isSaved = false;
                    food_complement.addToQuantity();
                    OrderDetails.setComplements(food_complement.getComplement_name());
                    OrderDetails.setComplements_prices(""+food_complement.getmPrice());
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }
}
