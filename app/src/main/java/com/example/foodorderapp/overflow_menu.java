package com.example.foodorderapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class overflow_menu extends AppCompatActivity {
    private static String option;
    private static int REQUEST_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_and_help);
        Toolbar toolbar = findViewById(R.id.overflow_menu_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        option = getIntent().getStringExtra("option");
        Toast.makeText(this, option, Toast.LENGTH_LONG).show();

        if(option.equals("About Restaurant")){
            getSupportActionBar().setTitle("About Restaurant");
            getSupportFragmentManager().beginTransaction().replace(R.id.overflow_frame, new restaurant_details_frag(), null).commit();
        }else if(option.equals("Help")){
            getSupportActionBar().setTitle("Help Page");
            getSupportFragmentManager().beginTransaction().replace(R.id.overflow_frame, new help_fragment(), null).commit();
        }else if(option.equals("Favorite")){
            getSupportActionBar().setTitle("My favorites");
            getSupportFragmentManager().beginTransaction();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(option.equals("About Restaurant")){
            getMenuInflater().inflate(R.menu.about_res_menu,menu);
            return true;
        }
        return true;
    }

    private void call_start(){
        if(ContextCompat.checkSelfPermission(overflow_menu.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(overflow_menu.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+"0556510555")));
        }
    }

    private void email(String res_name, String message, String to){
        Intent mail = new Intent(Intent.ACTION_SENDTO);
        mail.setData(Uri.parse("mailto:"));
        mail.putExtra(mail.EXTRA_EMAIL, to);
        mail.putExtra(mail.EXTRA_SUBJECT,"An e-mail to: " + res_name);
        mail.putExtra(mail.EXTRA_TEXT, message);

        if (mail.resolveActivity(getPackageManager()) != null){
            startActivity(mail);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.call_us:
                call_start();
                break;
            case R.id.email_us:
                email("nkansah restaurant", "I need your agent to be quick about the delivery", "nkansahisaac41@gmail.com");
                break;
        }
        return true;
    }

}
