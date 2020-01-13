package com.example.foodorderapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;

public class Payment_Details extends AppCompatActivity implements View.OnClickListener {
    private Button placeOrder;
    private FrameLayout momo_frame;
    private Button mtnBtn, tigoBtn, vodaBtn, airtelBtn;
    private RadioButton visaCard, cashOnDelivery, debitCard, creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        Toolbar toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        placeOrder = findViewById(R.id.place_orderBtn);
        placeOrder.setOnClickListener(this);
        mtnBtn = findViewById(R.id.mtnBtn);
        tigoBtn = findViewById(R.id.tigoBtn);
        vodaBtn = findViewById(R.id.vodaBtn);
        airtelBtn = findViewById(R.id.airtelBtn);
        cashOnDelivery = findViewById(R.id.cash_on_delivery);
        visaCard = findViewById(R.id.visa_card);
        debitCard = findViewById(R.id.debit_card);
        debitCard.setOnClickListener(this);
        visaCard.setOnClickListener(this);
        cashOnDelivery.setOnClickListener(this);
        mtnBtn.setOnClickListener(this);
        tigoBtn.setOnClickListener(this);
        vodaBtn.setOnClickListener(this);
        airtelBtn.setOnClickListener(this);

        RadioButton momo = findViewById(R.id.mobile_money);
        momo.setOnClickListener(this);
        momo_frame = findViewById(R.id.momo_frame);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.place_orderBtn:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setView(R.layout.custom_order_summary);
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                });
                dialog.show();
                break;
            case R.id.mobile_money:
                momo_frame.setVisibility(View.VISIBLE);
                break;
            case R.id.visa_card:
                momo_frame.setVisibility(View.GONE);
                break;
        }
    }
}
