package com.baqoba.bakingapp.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.adapters.RecipeListAdapter;
import com.baqoba.bakingapp.data.Recipe;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListAdapterOnClickHandler {

    private RecyclerView rvRecipes;
    ProgressDialog dialog;

    RecipeListAdapter mAdapter;
    public static ArrayList<Recipe> recipes = new ArrayList<>();
    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    GridLayoutManager layoutManager;
    // LinearLayoutManager layoutManager;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRecipes = (RecyclerView) findViewById(R.id.rv_recipes);

    /*    if(findViewById(R.id.android_me_linear_layout) != null){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
*/

        //Make call to AsyncTask
        new FetchBakesTask().execute();

    }

    @Override
    public void onClick(int clickedItemIndex) {
     //   Toast.makeText(MainActivity.this, recipes.get(clickedItemIndex).getName(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

        intent.putExtra("item_index", clickedItemIndex);
        startActivity(intent);
    }

    @Override
    public void onPause() {

        super.onPause();
        if (dialog != null)
            dialog.dismiss();
    }

    protected void loadList(ArrayList<Recipe> recipes) {

        //this method will be running on UI thread
        try {
            // Setup and Handover data to recyclerview
            //  layoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
            layoutManager = new GridLayoutManager(this, 2);
            // layoutManager = new LinearLayoutManager(this);

            mAdapter = new RecipeListAdapter(getApplicationContext(), this, recipes);
            rvRecipes.setLayoutManager(layoutManager);
            rvRecipes.setAdapter(mAdapter);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public class FetchBakesTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = ProgressDialog.show(MainActivity.this, "", "Loading...", true, false);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                Uri builtUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
                        .buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }

                JSONArray jArray = new JSONArray(buffer.toString());
                recipes = new ArrayList<>();
                for (int i = 0; i < jArray.length(); i++) {
                    recipes.add(new Recipe(jArray.getJSONObject(i)));
                }

                return recipes;
            } catch (Exception e) {
                e.printStackTrace();
                return recipes;
            } finally {
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            if ((dialog != null) && dialog.isShowing()) {
                dialog.dismiss();
            } else {
                Toast.makeText(getApplicationContext(), "dialog is not found", Toast.LENGTH_SHORT).show();
            }
            loadList(recipes);
        }
    }

}
