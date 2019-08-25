package com.changed.supun.kitchenmanager.Recipies;


import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    //logging
    private static final String LOG_TAG =
            NetworkUtils.class.getSimpleName();

    // Base URL for Books API.
    private static final String BOOK_BASE_URL = "https://www.food2fork.com/api/search?";

    // Parameter for the search string.
    private static final String API_KEY = "key";

    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";


    // Parameter that limits search results.
    private static final String MAX_RESULTS = "page";


    public String getBookInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {

            //request URI
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY, "90336513798832ceb94b73e38142877e")
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "1")
                    .build();
            //converting the URI to a URL Object
            URL requestURL = new URL(builtURI.toString());

            //opening the URL connection and make the request
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //getting the input stream
            InputStream inputStream = urlConnection.getInputStream();

            //creating a buffered reader from the input stream
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // using a string builder to hold the incoming response
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }
            if (builder.length() == 0) {
                return null;
            }
            bookJSONString = builder.toString();
        } catch (IOException e) {
            e.getStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }


}

