package com.baqoba.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baqoba.bakingapp.adapters.IngredientAdapter;
import com.baqoba.bakingapp.adapters.StepAdapter;
import com.baqoba.bakingapp.data.Ingredient;
import com.baqoba.bakingapp.data.Recipe;
import static com.baqoba.bakingapp.ui.MainActivity.recipes;
import static com.baqoba.bakingapp.ui.DetailActivity.mTwoPane;

import com.baqoba.bakingapp.R;
import com.baqoba.bakingapp.data.Step;

import java.util.ArrayList;

public class MasterListFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    private RecyclerView stepRecyclerView;
    TextView tvIngredients;
    View rootView;
    int index=0;
    public static ArrayList<Step> step= new ArrayList<>();
    public static ArrayList<Ingredient> ingredient = new ArrayList<>();
    public static Bundle bundle = new Bundle();

    private StepAdapter stepAdapter;

    LinearLayoutManager layoutManagerStep;
    public static int totalStep;

    public MasterListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        stepRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_step_list);
        tvIngredients = (TextView) rootView.findViewById(R.id.tv_ingredient);

        index = getActivity().getIntent().getExtras().getInt("item_index");
        Log.d("index", String.valueOf(index));
        Log.d("recipe_name", recipes.get(index).getName());
        Log.d("steps:", recipes.get(index).getStep().toString());
        step = recipes.get(index).getStep();
        ingredient = recipes.get(index).getIngredient();

      //  LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
         layoutManagerStep = new LinearLayoutManager(getActivity());

        stepAdapter = new StepAdapter(getActivity(), this, step, "R.layout.fragment_master_list");
        Log.d("stepAdapter", String.valueOf(stepAdapter));

        stepRecyclerView.setLayoutManager(layoutManagerStep);
        Log.d("getCount", String.valueOf(stepAdapter.getItemCount()));
        stepRecyclerView.setAdapter(stepAdapter);
        
        totalStep = stepAdapter.getItemCount();

        tvIngredients.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mTwoPane) {
                    Toast.makeText(getActivity(), "Click Ingredient!", Toast.LENGTH_LONG).show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    IngredientFragment ingredientFragment = new IngredientFragment();
                    //   stepsFragment.setArguments(bundle);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, ingredientFragment)
                            .commit();
                }else{
                    Intent intent =new Intent(getActivity(),IngredientActivity.class);
                    startActivity(intent);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onClick(int clickedItemIndex) {
        if(mTwoPane) {
            //    Toast.makeText(getActivity(), step.get(index).getShortDescription(), Toast.LENGTH_LONG).show();
            Toast.makeText(getActivity(), getActivity().toString(), Toast.LENGTH_LONG).show();


            bundle.putInt("item_index", clickedItemIndex);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.detail_container, stepsFragment)
                    .commit();
        }else{
            Intent intent =new Intent(getActivity(),IngredientActivity.class);
            intent.putExtra("item_index",clickedItemIndex);
            startActivity(intent);
        }

    }


    @Override
    public void onClick(View v) {
    }


/*
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
*/
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
 /*   public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/

}
