package com.changed.supun.kitchenmanager.DisplayProducts;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.changed.supun.kitchenmanager.DatabaseHelper;
import com.changed.supun.kitchenmanager.KitchenItem;
import com.changed.supun.kitchenmanager.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayProducts extends AppCompatActivity {
    //variable of the database helper class
    public DatabaseHelper myDb;
    //varibale to declare the recylcerview
    private RecyclerView kitchenRecyclerView;

    //arraylist to store the kitchen items
    public ArrayList<KitchenItem> kitchenList = new ArrayList<KitchenItem>();

    //variable to declare the button
    public Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_products);

        //insatnce of the database helper class to access it's methods
        myDb = new DatabaseHelper(this);

        //initilization of the recyclerview
        kitchenRecyclerView = (RecyclerView) findViewById(R.id.brands_lst);

        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        kitchenRecyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(kitchenRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        kitchenRecyclerView.addItemDecoration(dividerItemDecoration);

        //RecyclerView adapater
        final ProductFilterRecyclerViewAdapter recyclerViewAdapter = new
                ProductFilterRecyclerViewAdapter(getItems(), this);
        kitchenRecyclerView.setAdapter(recyclerViewAdapter);

        //initilization of the add button
        addButton = (Button) findViewById(R.id.button_findRecipe);


        //onClickListerner
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = false;

                for (int i = 0; i < recyclerViewAdapter.filterList.size(); i++) {
                    int available = recyclerViewAdapter.filterList.get(i).getAvailability();
                    int id = recyclerViewAdapter.filterList.get(i).getId();
                    //convert
                    String finalId = String.valueOf(id);

                    isUpdate = myDb.updateAvailability(available, finalId);


                }

                if (isUpdate == true) {
                        /*int length = recyclerViewAdapter.filterList.size();
                        String finallength = String.valueOf(length);*/
                    Log.d("Display Products", "items Updated");
                    Toast.makeText(DisplayProducts.this, "Items Added to Kitchen", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DisplayProducts.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private List<KitchenItem> getItems() {
        //arraylist to store db objects

        //populating the arraylist with db objects
        Cursor res = myDb.getAllDataAlphabetical();
        while (res.moveToNext()) {
            String itemName = res.getString(res.getColumnIndex("NAME"));
            double weight = res.getDouble(res.getColumnIndex("WEIGHT"));
            int availability = res.getInt(res.getColumnIndex("AVAILABILITY"));
            int id = res.getInt(res.getColumnIndex("_id"));
            String description = res.getString(res.getColumnIndex("DESCRIPTION"));
            double price = res.getDouble(res.getColumnIndex("PRICE"));
            kitchenList.add(new KitchenItem(itemName, weight, availability, id, description, price));
        }

        res.close();

        return kitchenList;
    }


}


