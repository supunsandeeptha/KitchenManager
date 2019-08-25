package com.changed.supun.kitchenmanager.Search;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.changed.supun.kitchenmanager.DatabaseHelper;
import com.changed.supun.kitchenmanager.R;

public class Search extends AppCompatActivity {

    //instance of the search adapter class
    private SearchAdapter searchAdapter;
    //instance of the database helper class
    DatabaseHelper myDb;
    //list view declaration
    ListView listView;
    //cursor variable declaration
    Cursor cursor;

    //for logging purposes
    private final static String TAG= Search.class.getName().toString();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //initializing the DB
         myDb = new DatabaseHelper(Search.this);
         //calling the getAllData method
        cursor = myDb.getAllData();
        //initializing the search adapter
        searchAdapter = new SearchAdapter(Search.this,cursor,0);
        //initializing the listview
        listView = (ListView) findViewById(R.id.kitchenItemList);
        //setting the adapter
        listView.setAdapter(searchAdapter);



    }

    @Override
    public void onResume(){
        super.onResume();

    }


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflating the menu which contains the search icon
        getMenuInflater().inflate(R.menu.menu, menu);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "onQueryTextSubmit ");
                    cursor = myDb.getKitchenItemListByKeyword(s);
                    if (cursor==null){
                        Toast.makeText(Search.this,"No records found!",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(Search.this, cursor.getCount() + " records found!",Toast.LENGTH_LONG).show();
                    }
                    searchAdapter.swapCursor(cursor);

                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    Log.d(TAG, "onQueryTextChange ");
                    cursor=myDb.getKitchenItemListByKeyword(s);
                    if (cursor!=null){
                        searchAdapter.swapCursor(cursor);
                    }
                    return false;
                }

            });

        }

        return true;

    }


}