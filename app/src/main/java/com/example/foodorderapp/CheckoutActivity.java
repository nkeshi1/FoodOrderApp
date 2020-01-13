package com.example.foodorderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout enterDeliveryTime, home_delivery_frame, pickup_frame;
    private RadioButton deliver_now, deliver_later;
    private Button home_delivery, pickup, payment_detailsBtn;
    private TextView minMaxBtn;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.checkout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.user_account:
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.checkout_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Checkout Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView sub_total = findViewById(R.id.sub_total_price);
        sub_total.setText(total_order_frag.cartCost.getText());
        enterDeliveryTime = findViewById(R.id.specific_time_framelayout);
        payment_detailsBtn = findViewById(R.id.payment_detailsBtn);
        payment_detailsBtn.setOnClickListener(this);
        deliver_now = findViewById(R.id.deliver_now_rbtn);
        deliver_later = findViewById(R.id.deliver_later_rbtn);
        minMaxBtn = findViewById(R.id.min_max_Btn);
        minMaxBtn.setOnClickListener(this);
        deliver_later.setOnClickListener(this);
        deliver_now.setOnClickListener(this);
        home_delivery_frame = findViewById(R.id.home_delivery_frame);
        pickup_frame = findViewById(R.id.pickup_frame);
        home_delivery = findViewById(R.id.home_delivery_Btn);
        home_delivery.setOnClickListener(this);
        pickup = findViewById(R.id.pickup_Btn);
        pickup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deliver_later_rbtn:
                enterDeliveryTime.setVisibility(View.VISIBLE);
                break;
            case R.id.deliver_now_rbtn:
                enterDeliveryTime.setVisibility(View.GONE);
                break;
            case R.id.pickup_Btn:
                minMaxBtn.setVisibility(View.VISIBLE);
                minMaxBtn.setBackgroundResource(R.drawable.ic_minimize_up_black_24dp);
                pickup.setBackgroundResource(R.drawable.delivery_background_select);
                home_delivery_frame.setVisibility(View.GONE);
                pickup_frame.setVisibility(View.VISIBLE);
                enterDeliveryTime.setVisibility(View.GONE);
                home_delivery.setBackgroundResource(R.drawable.delivery_background_unselect);
                break;
            case R.id.home_delivery_Btn:
                minMaxBtn.setVisibility(View.VISIBLE);
                minMaxBtn.setBackgroundResource(R.drawable.ic_minimize_up_black_24dp);
                home_delivery.setBackgroundResource(R.drawable.delivery_background_select);
                home_delivery_frame.setVisibility(View.VISIBLE);
                pickup_frame.setVisibility(View.GONE);
                pickup.setBackgroundResource(R.drawable.delivery_background_unselect);
                break;
            case R.id.min_max_Btn:
                minMaxBtn.setBackgroundResource(R.drawable.ic_maximize_black_24dp);
                home_delivery_frame.setVisibility(View.GONE);
                pickup_frame.setVisibility(View.GONE);
                break;
            case R.id.payment_detailsBtn:
                startActivity(new Intent(getApplicationContext(), Payment_Details.class));
                break;
        }
    }
}
