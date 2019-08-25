package com.changed.supun.kitchenmanager.Availability;

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

public class Availability extends AppCompatActivity {

    //variable of the database helper class
    public DatabaseHelper myDb;
    //variable to declare the recylcerview
    private RecyclerView kitchenRecyclerView;

    //arraylist to store the kitchen  items
    public ArrayList<KitchenItem> kitchenList = new ArrayList<KitchenItem>();

    //variable to declare the button
    public Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        //instance of the database helper class to access it's methods
        myDb = new DatabaseHelper(this);

        //initilization of the recyclerview
        kitchenRecyclerView = (RecyclerView) findViewById(R.id.brands_lst);

        //RecyclerView layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        //setting the layout of the recycler view
        kitchenRecyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(kitchenRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        kitchenRecyclerView.addItemDecoration(dividerItemDecoration);

        //RecyclerView adapater
        final AvailabilityFilterRecyclerViewAdapter recyclerViewAdapter = new
                AvailabilityFilterRecyclerViewAdapter(getItems(), this);
        kitchenRecyclerView.setAdapter(recyclerViewAdapter);

        //initilization of the add button
        saveButton = (Button) findViewById(R.id.button_findRecipe);

        //onClickListerner
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = false;
                //accessing the kitchen items through the recycler view class filter list
                for (int i = 0; i < recyclerViewAdapter.filterList.size(); i++) {
                    int available = recyclerViewAdapter.filterList.get(i).getAvailability();
                    int id = recyclerViewAdapter.filterList.get(i).getId();

                    //converting the id in to string
                    String finalId = String.valueOf(id);
                    Log.d("Availability/String id", finalId);

                    //updating the availability
                    isUpdate = myDb.updateAvailability(available, finalId);


                }
                if (isUpdate == true) {
                    //check the array
                        /*int length = recyclerViewAdapter.filterList.size();
                        String finallength = String.valueOf(length);*/
                    Log.d("Availability", "Items Removed");
                    Toast.makeText(Availability.this, "Items Availability Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Availability.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    //method to get the items from the database
    private List<KitchenItem> getItems() {
        //arraylist to store db objects

        //populating the arraylist with db objects
        Cursor res = myDb.getOnlyCheckedData();
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
