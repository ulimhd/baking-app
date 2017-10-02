package com.baqoba.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28/08/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Recipe> recipeResults;
    private Context mContext;

    private final RecipeListAdapterOnClickHandler mClickHandler;

    public RecipeListAdapter(Context context, RecipeListAdapterOnClickHandler clickHandler, List<Recipe> recipes) {
        this.mContext = context;
      //  recipeResults= new ArrayList<>();
        this.recipeResults = recipes;
        mClickHandler = clickHandler;

    }

    public interface RecipeListAdapterOnClickHandler {
        void onClick(int index);
    }

    protected class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivRecipeImage;
        private TextView tvRecipeName;


        public MyItemHolder(View itemView) {
            super(itemView);
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.iv_recipe_poster);
            tvRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.onClick(clickedPosition);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        viewHolder = getViewHolder(parent, inflater);
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View view = inflater.inflate(R.layout.list_recipes, parent, false);
        viewHolder = new MyItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Recipe result = recipeResults.get(position);
        final MyItemHolder myItemHolder = (MyItemHolder) holder;

        String recipeImage = result.getImage();
        String recipeName= result.getName();

        myItemHolder.tvRecipeName.setText(recipeName);

        if(recipeImage.isEmpty()) {

            switch (recipeName) {
                case "Nutella Pie":
                    Picasso.with(mContext).load(R.drawable.nutella_pie).into(myItemHolder.ivRecipeImage);
                    break;
                case "Brownies":
                    Picasso.with(mContext).load(R.drawable.brownies).into(myItemHolder.ivRecipeImage);
                    break;
                case "Yellow Cake":
                    Picasso.with(mContext).load(R.drawable.yellow_cake).into(myItemHolder.ivRecipeImage);
                    break;
                case "Cheesecake":
                    Picasso.with(mContext).load(R.drawable.cheesecake).into(myItemHolder.ivRecipeImage);
                    break;
                default:
                    break;
            }
        }else{
            Picasso.with(mContext).load(recipeImage).into(myItemHolder.ivRecipeImage);
        }

    }

    @Override
    public int getItemCount() {
        return recipeResults == null ? 0 : recipeResults.size();
    }

    public void add(Recipe r) {
        recipeResults.add(r);
        notifyItemInserted(recipeResults.size() - 1);
    }

    public void addAll(List<Recipe> trailerResults) {
        for (Recipe result : trailerResults) {
            add(result);
        }
    }
}
