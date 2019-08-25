package com.changed.supun.kitchenmanager.EditProducts;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;

import com.changed.supun.kitchenmanager.DatabaseHelper;
import com.changed.supun.kitchenmanager.KitchenItem;
import com.changed.supun.kitchenmanager.R;

import java.util.ArrayList;
import java.util.List;

public class EditProducts extends AppCompatActivity {

    //variable of the database helper class
    public DatabaseHelper myDb;

    //variable to declare the recyclerview
    private RecyclerView kitchenRecyclerView;

    //arraylist to store the kitchenlist items from the database
    ArrayList<KitchenItem> kitchenitemlist = new ArrayList<KitchenItem>();


    //adapter for the arraylist
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        //initiating the instance of the database helper class to access it's methods
        myDb = new DatabaseHelper(this);

        //initilization of the recyclerview
        kitchenRecyclerView = (RecyclerView) findViewById(R.id.brands_lst);

        //Recyclerview layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        kitchenRecyclerView.setLayoutManager(recyclerLayoutManager);

        //RecyclerView item decorator
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(kitchenRecyclerView.getContext(),
                        recyclerLayoutManager.getOrientation());
        kitchenRecyclerView.addItemDecoration(dividerItemDecoration);

        //RecyclerView adapater
        final EditProductsFilterRecyclerViewAdapter recyclerViewAdapter = new
                EditProductsFilterRecyclerViewAdapter(getItems(), this);
        kitchenRecyclerView.setAdapter(recyclerViewAdapter);

    }


    //method to get the items from the database
    private List<KitchenItem> getItems() {
        //arraylist to store db objects

        //populating the arraylist with db objects
        Cursor res = myDb.getAllData();
        while (res.moveToNext()) {
            String itemName = res.getString(res.getColumnIndex("NAME"));
            double weight = res.getDouble(res.getColumnIndex("WEIGHT"));
            int availability = res.getInt(res.getColumnIndex("AVAILABILITY"));
            int id = res.getInt(res.getColumnIndex("_id"));
            String description = res.getString(res.getColumnIndex("DESCRIPTION"));
            double price = res.getDouble(res.getColumnIndex("PRICE"));
            kitchenitemlist.add(new KitchenItem(itemName, weight, availability, id, description, price));
        }

        res.close();

        return kitchenitemlist;
    }
}
