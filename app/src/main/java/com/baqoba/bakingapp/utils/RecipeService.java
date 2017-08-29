package com.baqoba.bakingapp.utils;

import com.baqoba.bakingapp.data.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Admin on 28/08/2017.
 */

public interface RecipeService   {
    @GET("recipes")
    Call<Recipe> getRecipeList(
            //@Query("page") int pageIndex
    );
}
