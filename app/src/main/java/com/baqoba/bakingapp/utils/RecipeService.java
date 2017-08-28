package com.baqoba.bakingapp.utils;

import com.baqoba.bakingapp.data.RecipeModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 28/08/2017.
 */

public interface RecipeService   {
    @GET("recipes")
    Call<RecipeModel> getRecipeList(
            //@Query("page") int pageIndex
    );
}
