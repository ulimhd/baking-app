package com.baqoba.bakingapp.ui;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 28/08/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private List<Recipe> recipeResults;
    private Context mContext;

    private final RecipeListAdapterOnClickHandler mClickHandler;

    public RecipeListAdapter(Context context, RecipeListAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        recipeResults= new ArrayList<>();
        mClickHandler = clickHandler;
    }

    public interface RecipeListAdapterOnClickHandler {
        void onClick(Recipe currentRecipe);
    }

    protected class MyItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivRecipeImage;
        private TextView tvRecipeName;


        public MyItemHolder(View itemView) {
            super(itemView);
            Log.d("Test4", "test4");
            ivRecipeImage = (ImageView) itemView.findViewById(R.id.rv_recipes);
            tvRecipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
    /*        int adapterPosition = getAdapterPosition();
            String currentmovie = movieResults[adapterPosition];
            mClickHandler.onClick(currentmovie);

*/
            Recipe currentMovie = recipeResults.get(getAdapterPosition());
            //   mClickHandler.onClick(getAdapterPosition(), view);
            mClickHandler.onClick(currentMovie);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        Log.d("Test5", "Test5");
        viewHolder = getViewHolder(parent, inflater);
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        Log.d("TESTcreate", "bbbbb");
        View view = inflater.inflate(R.layout.list_recipes, parent, false);
        viewHolder = new MyItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Recipe result = recipeResults.get(position);
        final MyItemHolder myItemHolder = (MyItemHolder) holder;
        myItemHolder.tvRecipeName.setText(result.getName());

/*        String id = result.getKey();

        String thumbnailUrl = "http://img.youtube.com/vi/".concat(id).concat("/hqdefault.jpg");
        Log.d("THUMB:", thumbnailUrl);
        final MyItemHolder trailerVH = (MyItemHolder) holder;

        if(result.getId().equals("null")){
            Picasso.with(context).load(R.drawable.no_image).resize(185,277).into(trailerVH.trailerImage);
        } else {
            Picasso.with(context).load(thumbnailUrl).into(trailerVH.trailerImage);
        }
*/
    }

    @Override
    public int getItemCount() {
        return recipeResults == null ? 0 : recipeResults.size();
    }

    public void add(Recipe r) {
        recipeResults.add(r);
        Log.d("Test6", r.toString());
        notifyItemInserted(recipeResults.size() - 1);
    }

    public void addAll(List<Recipe> trailerResults) {
        for (Recipe result : trailerResults) {
            Log.d("result: ", result.toString());
            add(result);
            Log.d("Test7", "Test7");
        }
    }

}
