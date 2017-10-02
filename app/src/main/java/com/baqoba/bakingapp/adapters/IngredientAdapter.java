package com.baqoba.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Ingredient;

import java.util.List;

/**
 * Created by Admin on 31/08/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Ingredient> ingredientResults;
    private Context mContext;

    public IngredientAdapter(List<Ingredient> ingredient) {
        //this.mContext = context;
        //  recipeResults= new ArrayList<>();
        this.ingredientResults = ingredient;
    }

    protected class MyItemHolder extends RecyclerView.ViewHolder{
        private TextView tvIngredientName, tvQuantity, tvMeasure;


        public MyItemHolder(View itemView) {
            super(itemView);
            tvQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            tvMeasure = (TextView) itemView.findViewById(R.id.tv_measure);
            tvIngredientName = (TextView) itemView.findViewById(R.id.tv_ingredient_name);
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
        View view = inflater.inflate(R.layout.list_ingredient, parent, false);
        viewHolder = new MyItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Ingredient result = ingredientResults.get(position);
        final MyItemHolder myItemHolder = (MyItemHolder) holder;
        myItemHolder.tvQuantity.setText(String.valueOf(result.getQuantity()));
        myItemHolder.tvMeasure.setText(result.getMeasure());
        myItemHolder.tvIngredientName.setText(result.getIngredient());
        //TODO (1) Set for multipole view holder for StepAdapter: one for in fragment_master_list, one for used in step description
    }

    @Override
    public int getItemCount() {
        return ingredientResults == null ? 0 : ingredientResults.size();
    }
}
