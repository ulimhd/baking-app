package com.baqoba.bakingapp.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ulimhd on 29/08/17.
 */

public class RecipeJsonUtils {
    public static String[] getDataFromJson(Context context, String recipeJsonStr)
            throws JSONException {

        final String MOV_RESULT = "results";

        String[] parsedMovieData = null;

        JSONObject mJson = new JSONObject(recipeJsonStr);


        JSONArray movieArray = mJson.getJSONArray(MOV_RESULT);

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            /* These are the values that will be collected */
            String title;
            String poster_path;
            String overview;
            String rating;
            String release_date;

            JSONObject movie = movieArray.getJSONObject(i);

            title = movie.getString("title");
            poster_path = movie.getString("poster_path");
            overview = movie.getString("overview");
            rating = movie.getString("vote_average");
            release_date = movie.getString("release_date");


            parsedMovieData[i] = "title: " + title
                    + " poster_path: " + poster_path
                    + " overview: " + overview
                    + " rating: " + rating
                    + " release_date: " + release_date;
        }

        return parsedMovieData;
    }
}
