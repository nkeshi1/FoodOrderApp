package com.example.foodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class total_order_frag extends Fragment {
    private View view;
    private static API_INTERFACE api_interface;
    public static ListView totalOrder;
    public int cart_cost;
    public static TextView cartCost;
    private OrderDetailsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.total_orders_frag, container,false);
        totalOrder = view.findViewById(R.id.total_order_list);

        cartCost = view.findViewById(R.id.totalCartCost);
        ViewCompat.setTranslationZ(cartCost, 5);

        Button checkoutBtn = view.findViewById(R.id.checkoutBtn);
        loadOrder();
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isChanged()){
                    startActivity(new Intent(getContext(), CheckoutActivity.class).putExtra("sub_total", adapter.getSubTotal()));
                }else{
                    startActivity(new Intent(getContext(), CheckoutActivity.class).putExtra("sub_total", OrderDetails.getCart_cost()));
                }
            }
        });
        return view;
    }

    private void loadOrder(){
        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        Call<OrderDetails> orders_retrieved = api_interface.ordersRetrieved();
        orders_retrieved.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                for(int i = 0; i<response.body().getOrderDetails().size(); i++){
                    cart_cost += response.body().getOrderDetails().get(i).getTotal_cost();
                }
                adapter = new OrderDetailsAdapter(getActivity(), response.body().getOrderDetails(), cart_cost);
                adapter.notifyDataSetChanged();
                totalOrder.setAdapter(adapter);
                OrderDetails.setCart_cost(cart_cost);
                cartCost.setText("Ghc "+(OrderDetails.getCart_cost())+".00");
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
