
package com.baqoba.bakingapp.data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {

    private String name;
    private ArrayList<Ingredient> ingredient;
    private ArrayList<Step> step;
    private String servings;
    private String image;

    public Recipe(JSONObject recipe_json){
        try{
            this.name = recipe_json.getString("name");

            this.ingredient = new ArrayList<>();
            JSONArray ingredientJSONArray = recipe_json.getJSONArray("ingredients");
            for (int i = 0; i < ingredientJSONArray.length(); i++){
                ingredient.add(new Ingredient(ingredientJSONArray.getJSONObject(i)));
            }

            this.step = new ArrayList<>();
            JSONArray stepJSONArray = recipe_json.getJSONArray("steps");
            for (int i = 0; i < stepJSONArray.length(); i++){
                step.add(new Step(stepJSONArray.getJSONObject(i)));
            }

            this.servings = recipe_json.getString("servings");
            this.image = recipe_json.getString("image");

        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public String getName(){
        return name;
    }

    public ArrayList<Ingredient> getIngredient(){
        return ingredient;
    }

    public ArrayList<Step> getStep(){
        return step;
    }

    public String getServings(){
        return servings;
    }

    public String getImage(){
        return image;
    }
}
