package com.example.foodorderapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_plate_cart extends AppCompatActivity {
    private static API_INTERFACE api_interface;
    public static int totalOrder, totalPending = 10, totalCompleted = 8;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return true;
    }

    SharedPreferences.Editor sharedP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plates_cart);
        sharedP = getSharedPreferences("SHARED_PREF",MODE_PRIVATE).edit();
        sharedP.putBoolean("refresh_now", false);
        sharedP.apply();

        //Getting Orders from database
        getOrders();

        //Setting the initial fragment to load
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.viewPager, new total_order_frag(),"all_retrieved_orders").commit();

        final TabLayout tabLayout = findViewById(R.id.tabLayout);
        final ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getSupportActionBar().setSubtitle(totalOrder +" orders retrieved");
                        break;
                    case 1:
                        getSupportActionBar().setSubtitle(totalPending+ " orders pending");
                        break;
                    case 2:
                        getSupportActionBar().setSubtitle(totalCompleted+" orders completed");
                        break;
                    case 3:
                        getSupportActionBar().setSubtitle(totalCompleted+" orders completed");
                        break;
                }
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_page:
                //Toast.makeText(getApplicationContext(),"Page Refreshed", Toast.LENGTH_LONG).show();
                ProgressDialog dialog = new ProgressDialog(this);
                dialog.setMessage("Refreshing page...");
                dialog.setCancelable(true);
                dialog.setTitle("Please wait");
                dialog.show();
                getOrders();
                dialog.dismiss();
                break;
        }
        return true;
    }

    public void getOrders(){
        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        Call<OrderDetails> orders_retrieved = api_interface.ordersRetrieved();
        orders_retrieved.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
//                if(response.body().getOrderDetails().get(0).getResponse().equals("true")){
                totalOrder = response.body().getOrderDetails().size();
                Toolbar toolbar = findViewById(R.id.cart_toolbar);
                toolbar.setTitle("Cart");
                toolbar.setSubtitle(totalOrder +" orders retrieved");
                toolbar.setSubtitleTextColor(getColor(R.color.iconsUnselectedColor));
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
                //          }
            }

            @Override
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
