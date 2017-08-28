package com.baqoba.bakingapp.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.RecipeModel;
import com.baqoba.bakingapp.utils.RecipeApi;
import com.baqoba.bakingapp.utils.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListAdapterOnClickHandler{

    private RecyclerView rvRecipes;
    private ProgressBar pbLoading;
    private RecipeService recipeService;

    RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRecipes = (RecyclerView) findViewById(R.id.rv_recipes);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading_indicator);



        GridLayoutManager layoutManager
                = new GridLayoutManager(this, calculateNoOfColumns(this));

        mAdapter = new RecipeListAdapter(getApplicationContext(), this);
        rvRecipes.setLayoutManager(layoutManager);
        rvRecipes.setHasFixedSize(true);
        rvRecipes.setAdapter(mAdapter);

        recipeService = RecipeApi.getClient().create(RecipeService.class);	//1
        loadFirstPage();

    }

    private void loadRecipes() {
        Log.d("test1", "TEST 1 working");
        callReviewsApi(idPath).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                // Got data. Send it to adapter

                List<ReviewModel> results = fetchResult(response);
                mReviewIndicator.setVisibility(View.GONE);
                reviewAdapter.addAll(results);
                Log.d("Resutl" , results.toString());

            }

            @Override
            public void onFailure(Call<Reviews> call, Throwable t) {
                t.printStackTrace();
                // TODO: 08/11/16 handle failure
            }
        });

    }

    @Override
    public void onClick(RecipeModel currentRecipe) {

    }


    private List<RecipeModel> fetchResult(Response<RecipeModel> response) {
        RecipeModel recipeList = response.body();
        return recipeList.getResults();
    }



    private Call<RecipeModel> callRecipeApi() {
        return recipeService.getRecipeList();
    }


    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 100;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }


}
