
package com.baqoba.bakingapp.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient {

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredient(JSONObject ingredient_json){
        try{
            this.quantity = ingredient_json.getDouble("quantity");
            this.measure = ingredient_json.getString("measure");
            this.ingredient = ingredient_json.getString("ingredient");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

}
