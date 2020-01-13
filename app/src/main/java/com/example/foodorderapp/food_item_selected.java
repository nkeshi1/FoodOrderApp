package com.example.foodorderapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class food_item_selected extends AppCompatActivity{
    public static boolean isSaved = false;
    private Button increase_mainFood, reduce_mainFood;
    private int mainFoodPrice;
    private Complement complement;
    public OrderDetails order;
    private TextView mainFood_price, mainDish;
    private ListView comp_list;
    private String food_selected;
    private static API_INTERFACE api_interface;
    private Toolbar toolbar;
    private ComplementAdapter complementAdapter;
    private CartOrdersAdapter cartOrdersAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.food_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FrameLayout rootView = findViewById(R.id.rootLayout);
        if(item.getTitle().equals("Save")){
            complement.setMainDishPrice(mainFoodPrice);
            order = new OrderDetails();
            order.setClient_id("client/001");
            order.setOrder_id("");
            order.setMain_dish_price(mainFoodPrice);

            order.setMain_dish(food_selected);
            order.setTotal_cost(complement.getTotalPrice());
            //Toast.makeText(getApplicationContext(), "The price of "+OrderDetails.getComplements().get(0)+"::"+OrderDetails.getComplements_prices().get(0)+"\n The price of "+ OrderDetails.getComplements().get(1)+"::"+OrderDetails.getComplements_prices().get(1), Toast.LENGTH_LONG).show();
            storeOrder(order, OrderDetails.getComplements().toString(), OrderDetails.getComplements_prices().toString());
            //Toast.makeText(getApplicationContext(), OrderDetails.getComplements()+"", Toast.LENGTH_LONG).show();
            Snackbar snackbar = Snackbar.make(rootView, "Order saved. Your order cost is Ghc "+complement.getTotalPrice()+".00", Snackbar.LENGTH_LONG);
            TextView snackbarText = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            snackbarText.setTextSize(18);
            snackbar.setDuration(4000);
            snackbar.show();
            isSaved = true;
        }else if(item.getTitle().equals("Close")){
            onBackPressed();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String order_option = getIntent().getExtras().getString("order_option", "");
        String order_id = getIntent().getExtras().getString("order_id", "");
        String client_id = getIntent().getExtras().getString("client_id", "");

        if (order_option.equals("")) {
            Toast.makeText(getApplicationContext(), "Load initially", Toast.LENGTH_LONG).show();
            //Loading order details here...
        } else if (order_option.equals("View")) {
            toolbar.setTitle("View Order");
            loadDataById(order_id, client_id);
            //Loading order details here...
        } else if (order_option.equals("Edit")) {
            toolbar.setTitle("Edit Order");
            //Loading order details here...
            //Toast.makeText(getApplicationContext(), order_id+"::"+client_id, Toast.LENGTH_LONG).show();
            loadDataById(order_id, client_id);

           // mainFood_price.setText(""+holder.getMain_dish_price());
            increase_mainFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainFoodPrice = Integer.parseInt(mainFood_price.getText().toString());
                    mainFood_price.setText(""+increasePrice(mainFoodPrice));
                }
            });

            reduce_mainFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainFoodPrice = Integer.parseInt(mainFood_price.getText().toString());
                    mainFood_price.setText(""+decreasePrice(mainFoodPrice));
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_complement);
        comp_list = findViewById(R.id.complement_list);
        mainDish = findViewById(R.id.main_foodText);

        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        Call<Complement> complementCall = api_interface.food_complement();

        complementCall.enqueue(new Callback<Complement>() {
            @Override
            public void onResponse(Call<Complement> call, final Response<Complement> response) {
                if(response.body().getComplements_details().get(0).getResponse().equals("true")){
                    complementAdapter = new ComplementAdapter(food_item_selected.this, response.body().getComplements_details());
                    comp_list.setAdapter(complementAdapter);
                }else{
                    Toast.makeText(getApplicationContext(), "no data is loaded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Complement> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        food_selected = getIntent().getStringExtra("food_selected");
        mainDish.setText("How much for "+ food_selected);
        mainFood_price = findViewById(R.id.mainFood_price);
        mainFoodPrice = Integer.parseInt(mainFood_price.getText().toString());
        increase_mainFood = findViewById(R.id.increase_main_price);
        complement = new Complement(mainFoodPrice);
        increase_mainFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFoodPrice = Integer.parseInt(mainFood_price.getText().toString());
                mainFood_price.setText(""+increasePrice(mainFoodPrice));
            }
        });

        reduce_mainFood = findViewById(R.id.reduce_main_price);
        reduce_mainFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFoodPrice = Integer.parseInt(mainFood_price.getText().toString());
                mainFood_price.setText(""+decreasePrice(mainFoodPrice));
            }
        });

        toolbar = findViewById(R.id.food_selected_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getColor(R.color.toolbarTitleColor));
        getSupportActionBar().setTitle(food_selected);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(food_item_selected.this);
                builder.setCancelable(false);
                builder.setTitle("Cancel Food Order");
                builder.setMessage("All changes will be lost, still want to go back?");

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { onBackPressed();
                    }
                });

                if(isSaved){
                    onBackPressed();
                }else{
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    String order_id;
    public void storeOrder(OrderDetails order,final String comp_name, final String comp_price){
        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);

        Call<OrderDetails> orderDetails = api_interface.order_details(order);
        orderDetails.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                if(response.body().getResponse().equals("true")){
                    //Toast.makeText(getApplicationContext(), "Order saved with order_id "+ response.body().getOrder_id(), Toast.LENGTH_LONG).show();
                    order_id = response.body().getOrder_id();
                    Call<OrderDetails> complement_name = api_interface.complement_names(comp_name, order_id);
                    complement_name.enqueue(new Callback<OrderDetails>() {
                        @Override
                        public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                            if(response.body().getResponse().equals("true")){
                                Call<OrderDetails> complement_price = api_interface.complement_prices(comp_price, order_id);
                                complement_price.enqueue(new Callback<OrderDetails>() {
                                    @Override
                                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                                        if(response.body().getResponse().equals("true")){
                                            Toast.makeText(getApplicationContext(), "Order saved successfully", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<OrderDetails> call, Throwable t) {
                                       // Toast.makeText(getApplicationContext(), "comp_price "+t.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderDetails> call, Throwable t) {
                            //Toast.makeText(getApplicationContext(), "comp_name "+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private static int increasePrice(int price){
        isSaved = false;
        price += 1;
        return price;
    }

    private static int decreasePrice(int price){
        if(price > 5){
            isSaved = false;
            price -= 1;
        }
        return price;
    }

    private void loadDataById(String order_id, String client_id){
        //api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        Call<SetComplements> retrieveOrder = api_interface.order_info(order_id, client_id);
        retrieveOrder.enqueue(new Callback<SetComplements>() {
            @Override
            public void onResponse(Call<SetComplements> call, Response<SetComplements> response) {
                if(response.body().getComplements_details().get(0).getResponse().equals("true")){
                    food_selected = response.body().getComplements_details().get(0).getMain_dish();
                    mainDish.setText("How much for "+ food_selected);
                    //String comp=response.body().getComplements_details().get(0).getComplement_name();
                    //String[] complements =comp.substring(1,comp.length()-1).split(",");

                    comp_list.setVisibility(View.GONE);
                    ListView order_list = findViewById(R.id.order_list);
                    cartOrdersAdapter = new CartOrdersAdapter(getApplicationContext(), response.body().getComplements_details());
                    order_list.setAdapter(cartOrdersAdapter);

                    //String[] comp= complements;
                    //Toast.makeText(getApplicationContext(),,Toast.LENGTH_LONG).show();
                    //String[] complement_names = complements.to;
                    //for (String complement_name:complement_names) {
                        /*cartOrdersAdapter = new CartOrdersAdapter(getApplicationContext(), response.body().getComplements_details());
                        comp_list.setAdapter(cartOrdersAdapter);*/
                    //}
                }
            }

            @Override
            public void onFailure(Call<SetComplements> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
