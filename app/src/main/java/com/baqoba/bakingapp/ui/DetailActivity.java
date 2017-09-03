package com.baqoba.bakingapp.ui;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Step;

public class DetailActivity extends AppCompatActivity {
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(findViewById(R.id.android_me_linear_layout) != null){
         //   this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mTwoPane = true;
            Log.d("twopane, ", String.valueOf(mTwoPane));

            if(savedInstanceState == null) {
                Log.d("instancenull", "isnull");
                FragmentManager fragmentManager = getSupportFragmentManager();
                StepsFragment stepsFragment = new StepsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, stepsFragment)
                        .commit();
            }
            else{
                Log.d("instancenotnull", "notnull");
            }
        }else{
            mTwoPane = false;
            Log.d("twopane, ", String.valueOf(mTwoPane));
        }
    }

}
