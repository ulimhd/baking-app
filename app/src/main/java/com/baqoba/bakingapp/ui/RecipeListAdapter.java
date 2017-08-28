package com.baqoba.bakingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baqoba.bakingapp.R;

/**
 * Created by Admin on 28/08/2017.
 */

class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListAdapterViewHolder>  {

    private Context mContext;
    private final RecipeListOnClickHandler mClickHandler;
    private String[] mRecipeData;

    public RecipeListAdapter(Context context, RecipeListOnClickHandler clickHandler){
        this.mContext = context;
        mClickHandler = clickHandler;
    }

    interface RecipeListOnClickHandler {
        void onClick(String recipes);
    }

    @Override
    public RecipeListAdapter.RecipeListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.list_recipes;

        View view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
        view.setFocusable(true);

        return new RecipeListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.RecipeListAdapterViewHolder holder, int position) {
        String currentRecipe = mRecipeData[position];

    }

    @Override
    public int getItemCount() {
        if (null == mRecipeData) return 0;
        return mRecipeData.length;
    }

    class RecipeListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView recipeImg;
        final TextView recipeName;

        RecipeListAdapterViewHolder(View view) {
            super(view);

            recipeImg = (ImageView) view.findViewById(R.id.iv_recipe_poster);
            recipeName = (TextView) view.findViewById(R.id.tv_recipe_name);


            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String currentRecipe = mRecipeData[adapterPosition];
            mClickHandler.onClick(currentRecipe);
        }
    }

}
