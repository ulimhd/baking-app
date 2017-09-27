package com.baqoba.bakingapp.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baqoba.bakingapp.R;

public class IngredientActivity extends AppCompatActivity {
    public static Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        if(savedInstanceState == null) {
            String source = getIntent().getExtras().getString("source");

            if(source.equals("tvIngredients")){
                FragmentManager fragmentManager = getSupportFragmentManager();
                IngredientFragment ingredientFragment = new IngredientFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, ingredientFragment)
                        .commit();
            }else if(source.equals("recyclerView")){
                bundle.putInt("item_index", getIntent().getExtras().getInt("item_index"));

                FragmentManager fragmentManager = getSupportFragmentManager();
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, stepsFragment)
                        .commit();

            }
        }
    }
}
