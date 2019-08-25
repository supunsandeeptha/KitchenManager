package com.changed.supun.kitchenmanager;

import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterProducts extends AppCompatActivity {

    //declaration of the button
    private Button save;

    //declaration of the edit text fields
    EditText itemName;
    EditText itemWeight;
    EditText itemPrice;
    EditText itemDescription;

    //Database class variable
    public DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_products);

        //insatnce of the database helper class to access it's methods
        myDb = new DatabaseHelper(this);


        //initilization of the button
        save = (Button) findViewById(R.id.buttonSave);

        //initilization of the edit text fields
        itemName = (EditText) findViewById(R.id.editText_ItemName);
        itemWeight = (EditText) findViewById(R.id.editText_Weight);
        itemPrice = (EditText) findViewById(R.id.editText_Price);
        itemDescription = (EditText) findViewById(R.id.editText_Description);


        //setting the onClick Listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(v);
            }
        });


    }


    public void addData(View view) {

        //declaring and initializing variables to store weight and price
        double finalweight = 0;
        double finalprice = 0;


        //variable to store the name of the item
        String name = itemName.getText().toString();
        //validating the inputs
        if (!TextUtils.isEmpty(name)) {

            Snackbar.make(view, name, Snackbar.LENGTH_SHORT).show();
        } else {
            itemName.setError("Please Enter Item Name");
        }

        //convert the string to double
        String convertweight = itemWeight.getText().toString();
        //validating the inputs
        if (!TextUtils.isEmpty(convertweight)) {
            Snackbar.make(view, name, Snackbar.LENGTH_SHORT).show();
            finalweight = Double.parseDouble(convertweight);
        } else {
            itemWeight.setError("Please enter item weight");
        }


        //convert the string to double
        String convertPrice = itemPrice.getText().toString();
        //validating the inputs
        if (!TextUtils.isEmpty(convertPrice)) {
            Snackbar.make(view, convertPrice, Snackbar.LENGTH_SHORT).show();
            finalprice = Double.parseDouble(convertPrice);
        } else {
            itemPrice.setError("Please enter item Price");
        }


        //variable to store the description of the item
        String description = itemDescription.getText().toString();
        if (!TextUtils.isEmpty(description)) {
            Snackbar.make(view, description, Snackbar.LENGTH_SHORT).show();
        } else {
            itemDescription.setError("Please enter item description");
        }

        int availability = 0;
        //checking the response

        //validating whether all fields are filled
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(convertweight) || TextUtils.isEmpty(convertPrice) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "You Have to Fill All", Toast.LENGTH_SHORT).show();
        } else {

            //logging
            Log.d("Register Products", name);
            Log.d("Register Products", convertPrice);
            Log.d("Register Products", convertweight);
            Log.d("Register Products", description);

            //inserting the data to the database
            boolean isinserted = myDb.insertData(name, finalweight, finalprice, description, availability);

            if (isinserted) {
                //making the success toast
                Toast.makeText(RegisterProducts.this, "Item has been added successfully ", Toast.LENGTH_SHORT).show();

            } else {
                //making the unsuccessful toast
                Toast.makeText(RegisterProducts.this, "Error !!! Item not added ", Toast.LENGTH_SHORT).show();
            }
        }


    }


}
