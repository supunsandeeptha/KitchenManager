package com.changed.supun.kitchenmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.changed.supun.kitchenmanager.Availability.Availability;
import com.changed.supun.kitchenmanager.DisplayProducts.DisplayProducts;
import com.changed.supun.kitchenmanager.EditProducts.EditProducts;
import com.changed.supun.kitchenmanager.Recipies.Recipies;
import com.changed.supun.kitchenmanager.Search.Search;

public class MainActivity extends AppCompatActivity {

    //declaration of buttons used in the main menu
    private Button registerProducts;
    private Button displayProducts;
    private Button availability;
    private Button editProducts;
    private Button search;
    private Button recipies;
    private Button readMe;

    public DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the database helper class
        myDb = new DatabaseHelper(this);

        //initialization of register products button
        registerProducts = (Button) findViewById(R.id.registerProductsBtn);
        //setting the onclick listener
        registerProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openRegisterProducts();

            }
        });


        //initialization of display products button
        displayProducts = (Button) findViewById(R.id.displayProductsBtn);

        //setting the onclickListener
        displayProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDisplayProducts();

            }
        });

        //initialization of availability button
        availability = (Button) findViewById(R.id.availabilityBtn);

        //setting the onclickListener
        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAvailability();

            }
        });


        //initilization of edit products button
        editProducts = (Button) findViewById(R.id.editProductsBtn);

        //setting the onclick listener
        editProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openEditProducts();

            }
        });


        //initilization of search button
        search = (Button) findViewById(R.id.searchBtn);

        //setting the onclick listener
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSearch();
            }
        });


        //initilization of recipies button
        recipies = (Button) findViewById(R.id.recipiesBtn);

        //setting the onclick listener
        recipies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openRecipies();
            }
        });

        //initilization of readme button
        readMe = (Button) findViewById(R.id.button_readme);
        //setting the onclick listener
        readMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openReadMe();
            }
        });
    }


    //open register products method
    private void openRegisterProducts() {

        // new activity will be opened
        Intent intent = new Intent(this, RegisterProducts.class);
        startActivity(intent);

    }

    //open display products method
    private void openDisplayProducts() {

        //new activity will be opened
        Intent intent = new Intent(this, DisplayProducts.class);
        startActivity(intent);
    }

    //open availability method
    private void openAvailability() {

        Intent intent = new Intent(this, Availability.class);
        startActivity(intent);
    }

    //open edit products method
    private void openEditProducts() {

        Intent intent = new Intent(this, EditProducts.class);
        startActivity(intent);
    }

    //open search method
    private void openSearch() {

        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }


    //open recipies method
    private void openRecipies() {

        Intent intent = new Intent(this, Recipies.class);
        startActivity(intent);
    }

    //open the readme
    private void openReadMe() {

        Intent intent = new Intent(this, ReadMe.class);
        startActivity(intent);
    }


}

