package com.baqoba.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.adapters.IngredientAdapter;
import static com.baqoba.bakingapp.ui.MainActivity.recipes;
import static com.baqoba.bakingapp.ui.DetailActivity.mTwoPane;

import static com.baqoba.bakingapp.ui.MasterListFragment.ingredient;

public class IngredientFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

 //   private OnFragmentInteractionListener mListener;

    View rootView;
    TextView tvQuantity, tvMeasure, tvIngredient;

    LinearLayoutManager layoutManager;
    IngredientAdapter ingredientAdapter;
    RecyclerView ingredientRecyclerView;
    Button btnNext;

    public static Bundle bundle = new Bundle();

    public IngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientFragment newInstance(String param1, String param2) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_ingredient, container, false);
        ingredientRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_ingredients);
        btnNext = (Button)rootView.findViewById(R.id.btn_next_ingredient);

        layoutManager = new LinearLayoutManager(getActivity());

        ingredientAdapter = new IngredientAdapter(ingredient);
        Log.d("stepAdapter", String.valueOf(ingredientAdapter));

        ingredientRecyclerView.setLayoutManager(layoutManager);
        Log.d("getCount", String.valueOf(ingredientAdapter.getItemCount()));
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        btnNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Click Next!", Toast.LENGTH_LONG).show();
                bundle.putInt("item_index", 0);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                StepsFragment stepsFragment = new StepsFragment();
                stepsFragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.detail_container, stepsFragment)
                        .commit();

            }
        });

        return rootView;
    }


    @Override
    public void onClick(View v) {

    }
}
