package com.example.foodorderapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

public class food_drink_dessert extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.restaurants_menu_list, menu);
        MenuItem item = menu.findItem(R.id.search_food);
        SearchView searchFood = (SearchView) item.getActionView();
        searchFood.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_drink_dessert);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainframe,new food_list_display_frag(),null).commit();

        String title = getIntent().getStringExtra("res_chosen");
        final Toolbar toolbar = findViewById(R.id.fddtoolbar);
        toolbar.setTitle(title);
        toolbar.setSubtitle("Food");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.iconsUnselectedColor));
        toolbar.setNavigationIcon(R.drawable.ic_navigate_white_black_30dp);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final BottomNavigationView navigationView = findViewById(R.id.bottomNav);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                toolbar.setSubtitle(""+menuItem);
                if(menuItem.getTitle().equals("Food")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,new food_list_display_frag(), null).commit();
                }else if(menuItem.getTitle().equals("Drinks")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,new drinks_list_frag(), null).commit();
                }else if(menuItem.getTitle().equals("Dessert")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainframe,new desserts_list_display_frag(), null).commit();
                }
                return true;
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("Search")){

                }else if(menuItem.getTitle().equals("About Restaurant") || menuItem.getTitle().equals("Help") || menuItem.getTitle().equals("Favorite")){
                    startActivity(new Intent(getApplicationContext(), overflow_menu.class).putExtra("option",menuItem.getTitle()));
                }
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.plates_cart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),activity_plate_cart.class));
            }
        });

    }
}
