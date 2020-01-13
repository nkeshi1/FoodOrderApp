package com.example.foodorderapp;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsAdapter extends ArrayAdapter<OrderDetails> {
    private TextView copies;
    private int orderTotalCost;
    private static int cost;
    private int c;
    private boolean isChanged = false;
    private static API_INTERFACE api_interface;

    public OrderDetailsAdapter(Context context, ArrayList<OrderDetails> orderDetails, int cost) {
        super(context, 0, orderDetails);
        this.cost = cost;
    }

    public int getSubTotal(){
        return c;
    }

    public boolean isChanged(){
        return isChanged;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final OrderDetails order = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_summary_on_plate, parent, false);
        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);

        TextView order_status = convertView.findViewById(R.id.order_status);
        final TextView order_cost = convertView.findViewById(R.id.plate_cost);

        if (order.getComp_names().isEmpty()){

        }else{
            String[] complement_names = order.getComp_names().trim().substring(1,order.getComp_names().length()-1).split(",");

            String complements = "", conjunction = ",";
            for(int i = 0; i<complement_names.length; i++){
                if((complement_names.length) -1 == i) {
                    complements += complement_names[i];
                }else if((complement_names.length) -2 == i){
                    conjunction = " and";
                    complements += complement_names[i] + conjunction;
                }else{
                    complements += complement_names[i]+ conjunction;
                }
            }
            TextView orderDetails = convertView.findViewById(R.id.order_sum);
            if (complements.equals("")){
                orderDetails.setText(order.getMain_dish());
            }else{
                orderDetails.setText(order.getMain_dish() + " with "+ complements);
            }
        }

        copies = convertView.findViewById(R.id.complement_price);
        copies.setText(""+order.getOrder_copies());

        orderTotalCost = order.getTotal_cost() * Integer.parseInt(copies.getText().toString());
        order_cost.setText("Ghc " + orderTotalCost +".00");
        Button increase = convertView.findViewById(R.id.increase_comp_price);
        Button reduce = convertView.findViewById(R.id.reduce_comp_price);
        increase.setFocusable(false);
        reduce.setFocusable(false);

        final TextView menuFood = convertView.findViewById(R.id.menuFood);
        final PopupMenu popupMenu = new PopupMenu(getContext(), menuFood);
        popupMenu.getMenuInflater().inflate(R.menu.cart_list_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_order:
                        getContext().startActivity(new Intent(getContext(), food_item_selected.class).putExtra("order_option", item.getTitle()).putExtra("order_id", order.getOrder_id()).putExtra("client_id", order.getClient_id()));
                        break;
                    case R.id.view_order:
                        getContext().startActivity(new Intent(getContext(), food_item_selected.class).putExtra("order_option", item.getTitle()).putExtra("order_id", order.getOrder_id()).putExtra("client_id",order.getClient_id()));
                        break;
                    case R.id.delete_order:
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setTitle("Delete Order");
                        builder.setMessage("Are you sure you want to delete this order?");
                        builder.setCancelable(false);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final ProgressDialog progressBar = new ProgressDialog(getContext());
                                progressBar.setTitle("Delete Order");
                                progressBar.setMessage("Please wait a minute...");
                                progressBar.setCancelable(false);
                                progressBar.show();

                                //Making a call to database to delete order by id...
                                Call<OrderDetails> deleteOrder = api_interface.deleteOrder(order.getOrder_id());
                                deleteOrder.enqueue(new Callback<OrderDetails>() {
                                    @Override
                                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                                        if(response.body().getOrderDetails() != null){
                                            if(response.body().getOrderDetails().get(0).getResponse().equals("true")){
                                                Toast.makeText(getContext(), "Order Deleted", Toast.LENGTH_LONG).show();
                                                notifyDataSetChanged();
                                                for(int i = 0; i<response.body().getOrderDetails().size(); i++){
                                                    c += response.body().getOrderDetails().get(i).getTotal_cost();
                                                }
                                                OrderDetailsAdapter adapter = new OrderDetailsAdapter(getContext(), response.body().getOrderDetails(), cost);
                                                total_order_frag.totalOrder.setAdapter(adapter);
                                                activity_plate_cart.totalOrder -= 1;
                                                notifyDataSetChanged();
                                                OrderDetails.setCart_cost(cost);
                                                total_order_frag.cartCost.setText("Ghc "+c+".00");
                                                progressBar.dismiss();
                                            }
                                        }else{
                                            notifyDataSetChanged();
                                            for(int i = 0; i<response.body().getOrderDetails().size(); i++){
                                                c += response.body().getOrderDetails().get(i).getTotal_cost();
                                            }
                                            OrderDetailsAdapter adapter = new OrderDetailsAdapter(getContext(), response.body().getOrderDetails(), cost);
                                            total_order_frag.totalOrder.setAdapter(adapter);
                                            activity_plate_cart.totalOrder -= 1;
                                            notifyDataSetChanged();
                                            OrderDetails.setCart_cost(cost);
                                            total_order_frag.cartCost.setText("Ghc "+c+".00");
                                            Toast.makeText(getContext(), "No order left", Toast.LENGTH_LONG).show();
                                            progressBar.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetails> call, Throwable t) {
                                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.dismiss();
                                    }
                                });
                            }
                        });

                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder.create().dismiss();
                            }
                        });
                        builder.create().show();
                        break;
                }
                return true;
            }
        });

        menuFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.addOrderCopy();
                notifyDataSetChanged();
                copies.setText(""+order.getOrder_copies());
                order_cost.setText("Ghc " + orderTotalCost +".00");
                total_order_frag.cartCost.setText("Ghc "+(cost+order.getTotal_cost())+".00");
                cost += order.getTotal_cost();
                notifyDataSetChanged();
            }
        });

        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order.removeOrderCopy();
                notifyDataSetChanged();
                copies.setText(""+order.getOrder_copies());
                order_cost.setText("Ghc " + orderTotalCost +".00");
                total_order_frag.cartCost.setText("Ghc "+(cost-order.getTotal_cost())+".00");
                cost -= order.getTotal_cost();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
