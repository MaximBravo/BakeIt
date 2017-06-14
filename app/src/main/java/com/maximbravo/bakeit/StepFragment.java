package com.maximbravo.bakeit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepFragment extends Fragment {
    private int position;
    private Recipe recipe;
    private OnFabSelected mCallbacks;
    public StepFragment() {

    }

    public interface OnFabSelected {
        public void onNextSelected(Recipe recipe, int stepPosition);
        public void onPrevSelected(Recipe recipe, int currentPosition);
    }

    public void setPosition(Recipe recipe, int position){
        this.position = position;
        this.recipe = recipe;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mCallbacks = (OnFabSelected) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mCallbacks = (OnFabSelected) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        Step currentStep = recipe.getSteps().get(position);
        TextView descriptionTextView = (TextView) rootView.findViewById(R.id.step_description);
        descriptionTextView.setText(currentStep.getDescription());
        TextView shortDescriptionTextView = (TextView) rootView.findViewById(R.id.step_short_description);
        shortDescriptionTextView.setText(currentStep.getShortDescription());

        FloatingActionButton next = (FloatingActionButton) rootView.findViewById(R.id.next_fab);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onNextSelected(recipe, position);
            }
        });

        FloatingActionButton prev = (FloatingActionButton) rootView.findViewById(R.id.prev_fab);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallbacks.onPrevSelected(recipe, position);
            }
        });
        return rootView;
    }
}
