package com.changed.supun.kitchenmanager.Recipies;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Recipies extends AppCompatActivity {

    //variable of the NetworkUtils class to access it's methods
    public static NetworkUtils networkClass = new NetworkUtils();
    //variable of the database helper class
    public DatabaseHelper myDb;
    //variable to declare the recyclerView
    private RecyclerView kitchenRecyclerView;

    //variable to declare the JsonRecipe Recycler view
    public RecyclerView jsonRecipeView;

    //array list
    public ArrayList<KitchenItem> kitchenList = new ArrayList<KitchenItem>();

    //Array list of Json Object Model
    public static ArrayList<JsonObjectModel> jsonArray = new ArrayList<JsonObjectModel>();
    //variable to declare the recipe buttons
    Button findRecipe;
    Button clearRecipe;

    //variable for the recycler view Adapter
    public RecipeViewerRecyclerViewAdapter recipeViewerRecyclerViewAdapter;

    //variable for the runnable
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipies);


        //instance of the database class
        myDb = new DatabaseHelper(this);

        //instance of the network utils class


        //initializing of the recycler view
        kitchenRecyclerView = (RecyclerView) findViewById(R.id.brands_lst);

        //recycler view layout manager
        LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(this);
        //setting the layout of the recycler view
        kitchenRecyclerView.setLayoutManager(recyclerLayoutManager);

        //recycler view item decorator
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(kitchenRecyclerView.getContext(), recyclerLayoutManager.getOrientation());
        kitchenRecyclerView.addItemDecoration(dividerItemDecoration);

        //recycler view adapter
        final RecipiesFilterRecyclerViewAdapter recyclerViewAdapter = new RecipiesFilterRecyclerViewAdapter(getItems(), this);
        kitchenRecyclerView.setAdapter(recyclerViewAdapter);

        /*<!!----------------------------------------------------Recipe View Recycler View Variables ----------------------------------------------------------------------------- */

        //json recipe recycler view
        jsonRecipeView = (RecyclerView) findViewById(R.id.recipe_list);

        //Recycler view layout manager
        LinearLayoutManager recipeLayoutManager = new LinearLayoutManager(this);

        //setting the layout of the recycler view
        jsonRecipeView.setLayoutManager(recipeLayoutManager);

        //reciperecycler view item decorator
        DividerItemDecoration recipeDividerItemDecoration = new DividerItemDecoration(jsonRecipeView.getContext(), recipeLayoutManager.getOrientation());
        jsonRecipeView.addItemDecoration(recipeDividerItemDecoration);

        recipeViewerRecyclerViewAdapter = new RecipeViewerRecyclerViewAdapter(jsonArray, Recipies.this);
        jsonRecipeView.setAdapter(recipeViewerRecyclerViewAdapter);

        //initializing the buttons
        findRecipe = (Button) findViewById(R.id.button_findRecipe);
        clearRecipe = (Button) findViewById(R.id.button_clearRecipe);

        //setting the onClick Listener
        findRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Recipies.this, "Please wait, we are fetching recipes !!!", Toast.LENGTH_SHORT).show();
                //variable to store the selected items
                String food = "";
                //getting the selected items from the arraylist and adding to the string
                for (int i = 0; i < recyclerViewAdapter.checkBoxItems.size(); i++) {
                    food += recyclerViewAdapter.checkBoxItems.get(i) + ",";
                }

                //calling the method and passing the selected foods to get the recipes
                loadDataFromServer(food);
                try {
                    mHandler.removeCallbacks(hMyTimeTask);
                    //        Parameters
                    //        r  The Runnable that will be executed.
                    //        delayMillis  The delay (in milliseconds) until the Runnable will be executed.
                    mHandler.postDelayed(hMyTimeTask, 9000); // delay 1 second
                } catch (Exception e) {
                    String exception = e.toString();
                    //logging
                    Log.d("Recipes", exception);
                }
                //recycler view adapter
                //  recipeViewerRecyclerViewAdapter.notifyDataSetChanged();

            }
        });

        //setting the onclick listener for the clear recipe button
        clearRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clearing the ararylist
                jsonArray.clear();

                //resetting the adapter
                recipeViewerRecyclerViewAdapter.notifyDataSetChanged();


            }
        });
    }

    //getting items from the database
    private List<KitchenItem> getItems() {
        //arraylist to store db objects

        //populating the arraylist with db objects ( Only Checked Items)
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

    //method to load the information from the server
    public static void loadDataFromServer(String food) {

        AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return networkClass.getBookInfo(strings[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray itemsArray = jsonObject.getJSONArray("recipes");

                    int i = 0;
                    String title = null;
                    String URL = null;

                    while (i < itemsArray.length()) {
                        // Get the current item information.
                        JSONObject food = itemsArray.getJSONObject(i);


                        // Try to get the URL and title from the current item,
                        // catch if either field is empty and move on.
                        try {
                            title = food.getString("title");
                            URL = food.getString("source_url");
                            jsonArray.add(new JsonObjectModel(title, URL));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // Move to the next item.
                        i++;
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                } finally {

                    // return jsonArray;
                }


            }


        };

        task.execute(food);

    }

    private Runnable hMyTimeTask = new Runnable() {
        public void run() {
            //checking whether recipes are found or not
            if (jsonArray.isEmpty()) {
                Toast.makeText(Recipies.this, "No Recipes Found !!! Try again with another ingredient ", Toast.LENGTH_SHORT).show();
            }
            //notifying the recycler view adapter
            recipeViewerRecyclerViewAdapter.notifyDataSetChanged();

        }
    };
}
