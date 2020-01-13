package com.example.foodorderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static API_INTERFACE api_interface;
    TextView res_one_name, res_two_name, res_three_name;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu_items,menu);
//        MenuItem item = menu.findItem(R.id.search_food);
//        SearchView searchRestaurant = (SearchView) item.getActionView();
//        searchRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Restaurants");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarTitleColor));
        setSupportActionBar(toolbar);

        api_interface = API_CLIENT.getApiClient().create(API_INTERFACE.class);
        Call<Restaurants> res_details = api_interface.restaurant_details();
        res_details.enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                res_one_name = findViewById(R.id.res_one_name);
                res_one_name.setText(response.body().getDatails().get(0).getRestaurant_name());

                res_two_name = findViewById(R.id.res_two_name);
                res_two_name.setText(response.body().getDatails().get(1).getRestaurant_name());

                res_three_name = findViewById(R.id.res_three_name);
                res_three_name.setText(response.body().getDatails().get(2).getRestaurant_name());
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Search Button")) {

                }else if(menuItem.getTitle().equals("Favorite")){
                    startActivity(new Intent(getApplicationContext(), overflow_menu.class).putExtra("option",menuItem.getTitle()));
                }
                return true;
            }
        });

        findViewById(R.id.res_one).setOnClickListener(this);
        findViewById(R.id.res_two).setOnClickListener(this);
        findViewById(R.id.res_three).setOnClickListener(this);
        findViewById(R.id.res_four).setOnClickListener(this);
        findViewById(R.id.res_five).setOnClickListener(this);
        findViewById(R.id.res_six).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.res_one:
                Intent intent = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent.putExtra("res_chosen",res_one_name.getText());
                restaurant_details_frag.currentRes = 0;
                startActivity(intent);
                break;
            case R.id.res_two:
                Intent intent2 = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent2.putExtra("res_chosen",res_two_name.getText());
                restaurant_details_frag.currentRes = 1;
                startActivity(intent2);
                break;
            case R.id.res_three:
                Intent intent3 = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent3.putExtra("res_chosen",res_three_name.getText());
                restaurant_details_frag.currentRes = 2;
                startActivity(intent3);
                break;
            case R.id.res_four:
                Intent intent4 = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent4.putExtra("res_chosen","Restaurant Four");
                startActivity(intent4);
                break;
            case R.id.res_five:
                Intent intent5 = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent5.putExtra("res_chosen","Restaurant Five");
                startActivity(intent5);
                break;
            case R.id.res_six:
                Intent intent6 = new Intent(getApplicationContext(),food_drink_dessert.class);
                intent6.putExtra("res_chosen","Restaurant Six");
                startActivity(intent6);
                break;
        }
    }
}
