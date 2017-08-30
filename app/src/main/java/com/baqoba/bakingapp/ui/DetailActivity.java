package com.baqoba.bakingapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baqoba.bakingapp.R;

public class DetailActivity extends AppCompatActivity {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(findViewById(R.id.android_me_linear_layout) != null){
            mTwoPane = true;
        }
    }
}
