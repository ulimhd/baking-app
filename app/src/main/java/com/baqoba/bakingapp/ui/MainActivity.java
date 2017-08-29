package com.baqoba.bakingapp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Recipe;
import com.baqoba.bakingapp.utils.RecipeApi;
import com.baqoba.bakingapp.utils.RecipeService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListAdapterOnClickHandler{

    private RecyclerView rvRecipes;
    private ProgressBar pbLoading;
    private RecipeService recipeService;
    ProgressDialog pdLoading;

    RecipeListAdapter mAdapter;
    public static ArrayList<Recipe> recipes = new ArrayList<>();
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading_indicator);



        layoutManager
                = new GridLayoutManager(this, calculateNoOfColumns(this));

        mAdapter = new RecipeListAdapter(getApplicationContext(), this);
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
        rvRecipes.setAdapter(mAdapter);

        pdLoading = null;
        //Make call to AsyncTask
        new AsyncFetch().execute();

    }



    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 100;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    @Override
    public void onClick(Recipe currentRecipe) {
        Toast.makeText(MainActivity.this, currentRecipe.getName().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause(){

        super.onPause();
        if(pdLoading != null)
            pdLoading.dismiss();
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        //pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
         //   pdLoading.setMessage("\tLoading...");
         //   pdLoading.setCancelable(false);
         //   pdLoading.show();
            pdLoading = ProgressDialog.show(MainActivity.this,"","Loading...", true,false);

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                //TODO (1) : check why not succeesfu
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8"))   ;
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    Log.d("unsuc:", "unsuc");
                    return ("[]");

                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<Recipe> data=new ArrayList<>();

            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    Recipe recipeData = new Recipe();
                    String name=json_data.getString("servings");
                    recipeData.setName(name);
                    data.add(recipeData);
                }

                // Setup and Handover data to recyclerview

                rvRecipes.setLayoutManager(layoutManager);
                rvRecipes.setHasFixedSize(true);
                rvRecipes.setAdapter(mAdapter);

            } catch (JSONException e) {
                Log.d("what", e.toString());
                e.printStackTrace();
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }

}
