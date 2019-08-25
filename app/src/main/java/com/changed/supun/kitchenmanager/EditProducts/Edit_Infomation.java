package com.changed.supun.kitchenmanager.EditProducts;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.changed.supun.kitchenmanager.DatabaseHelper;
import com.changed.supun.kitchenmanager.R;

public class Edit_Infomation extends AppCompatActivity {

    //variables
    EditText itemName;
    EditText itemWeight;
    EditText itemPrice;
    EditText itemDescription;


    //TextView Variable
    TextView itemID;

    //spinner
    Spinner spinner;
    //Save Button
    Button saveButton;

    //Database helper class variable
    public DatabaseHelper myDb;
    //String variable for String id
    public String finalID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__infomation);

        //instance of the database helper class to access it's methods
        myDb = new DatabaseHelper(this);

        //initialization
        itemName = (EditText) findViewById(R.id.editText_ItemName);
        itemWeight = (EditText) findViewById(R.id.editText_Weight);
        itemPrice = (EditText) findViewById(R.id.editText_Price);
        itemDescription = (EditText) findViewById(R.id.editText_Description);
        itemID = (TextView) findViewById(R.id.item_id);
        spinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // itemAvailability = (EditText) findViewById(R.id.editText_availability);

        //button initialization
        saveButton = (Button) findViewById(R.id.buttonSave);

        //getting the values from the editproducts filter activity
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        double weight = intent.getDoubleExtra("weight", 0.0);
        double price = intent.getDoubleExtra("price", 0.0);
        int availability = intent.getIntExtra("availability", 0);
        String description = intent.getStringExtra("description");

        //setting the item ID
        finalID = String.valueOf(id);
        itemID.setText(finalID);
        //setting the details to edit texts
        itemName.setText(name);

        //converting to string and setting the weight
        String finalWeight = String.valueOf(weight);
        itemWeight.setText(finalWeight);
        //converting to string and setting the price
        String finalPrice = String.valueOf(price);
        itemPrice.setText(finalPrice);
        //setting the availability
        if (availability == 1) {
            spinner.setSelection(0);

        } else {
            spinner.setSelection(1);
        }
        //setting the description
        itemDescription.setText(description);

        //setting the onclick listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData(v);
            }
        });

    }


    public void updateData(View view) {
        double finalWeight = 0;
        double finalPrice = 0;
        int finalAvailability;
        //name
        String name = itemName.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            Snackbar.make(view, name, Snackbar.LENGTH_SHORT).show();
        } else {
            itemName.setError("Please enter the Item Name");
        }
        //weight
        String weight = itemWeight.getText().toString();
        //validating the inputs
        if (!TextUtils.isEmpty(weight)) {
            finalWeight = Double.parseDouble(weight);
            Snackbar.make(view, weight, Snackbar.LENGTH_SHORT).show();
        } else {
            itemWeight.setError("Please enter the Item weight");
        }

        //price
        String price = itemPrice.getText().toString();
        //validating the price
        if (!TextUtils.isEmpty(price)) {
            finalPrice = Double.parseDouble(price);
            Snackbar.make(view, price, Snackbar.LENGTH_SHORT).show();
        } else {
            itemPrice.setError("Please enter the Item Price");
        }

        //description
        String description = itemDescription.getText().toString();
        //validating the input
        if (!TextUtils.isEmpty(description)) {
            Snackbar.make(view, weight, Snackbar.LENGTH_SHORT).show();
        } else {
            itemDescription.setError("Please enter the Item Description");
        }


        //availability
        String availability = spinner.getSelectedItem().toString();

        //changing the availability based on the user input
        if (availability.equalsIgnoreCase("Available")) {
            finalAvailability = 1;
        } else {
            finalAvailability = 0;
        }

        //validating all fields
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(description)) {

            Toast.makeText(this, "Pleas Fill All Details", Toast.LENGTH_SHORT).show();

        } else {
            boolean isUpdate = myDb.updateAllData(finalID, name, finalWeight, finalPrice, description, finalAvailability);
            if (isUpdate == true) {

                Toast.makeText(Edit_Infomation.this, "Data Updated", Toast.LENGTH_SHORT).show();
                //setting the text fields to empty state
                itemName.setText("");
                itemPrice.setText("");
                itemWeight.setText("");
                itemDescription.setText("");
                itemID.setText("");

            } else {

                Toast.makeText(Edit_Infomation.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                //setting the text fields to empty state
                itemName.setText("");
                itemPrice.setText("");
                itemWeight.setText("");
                itemDescription.setText("");
                itemID.setText("");
            }
        }


    }
}
