package com.baqoba.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Recipe;
import com.baqoba.bakingapp.ui.DetailActivity;
import com.baqoba.bakingapp.ui.MainActivity;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.baqoba.bakingapp.ui.MainActivity.recipes;

/**
 * Created by Admin on 22/09/2017.
 */

public class WidgetViews implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;

    public WidgetViews(Context applicationContext) {
        mContext = applicationContext;
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        try {
            new FetchBakesTask().execute().get();
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.baking_list_widget);
        try {
            rv.setImageViewBitmap(R.id.icon, BitmapFactory.decodeStream(new URL(recipes.get(position).getImage()).openConnection().getInputStream()));
        } catch (IOException e) {
        }
        rv.setTextViewText(R.id.name, recipes.get(position).getName());
        rv.setTextViewText(R.id.servings, mContext.getString(R.string.servings) + " " + recipes.get(position).getServings());
        for (int i=0;i<recipes.get(position).getIngredient().size();i++){
            RemoteViews  ing= new RemoteViews(mContext.getPackageName(), R.layout.list_ingredient);
            ing.setTextViewText(R.id.tv_ingredient_name,recipes.get(position).getIngredient().get(i).getIngredient());
            ing.setTextViewText(R.id.tv_measure,recipes.get(position).getIngredient().get(i).getMeasure());
            ing.setTextViewText(R.id.tv_quantity,recipes.get(position).getIngredient().get(i).getQuantity()+"");
            rv.addView(R.id.ingerdient_list,ing);
        }

        Intent intent = new Intent();
        intent.putExtra("item", position);
        rv.setOnClickFillInIntent(R.id.bacckground, intent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public class FetchBakesTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected ArrayList<Recipe> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                Uri builtUri = Uri.parse("https://d17h27t6h515a5.cloudfront.net/topher/2017/March/58d1537b_baking/baking.json")
                        .buildUpon()
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JSONArray movieArray = new JSONArray(buffer.toString());
                recipes = new ArrayList<>();
                for (int i = 0; i < movieArray.length(); i++) {
                    recipes.add(new Recipe(movieArray.getJSONObject(i)));
                    Log.e("name: ", recipes.get(i).getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        reader.close();
                    }
                } catch (Exception e) {
                }
                return recipes;
            }
        }
    }
}
