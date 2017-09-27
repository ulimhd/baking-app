package com.baqoba.bakingapp.ui;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Step;
import static com.baqoba.bakingapp.ui.MainActivity.recipes;

public class DetailActivity extends AppCompatActivity {
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int index = getIntent().getExtras().getInt("item_index");

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(recipes.get(index).getName());
        }

        if(findViewById(R.id.android_me_linear_layout) != null){
            this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mTwoPane = true;

            if(savedInstanceState == null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                IngredientFragment ingredientFragment = new IngredientFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, ingredientFragment)
                        .commit();
            }

        }else{
            mTwoPane = false;
        }
    }

}
