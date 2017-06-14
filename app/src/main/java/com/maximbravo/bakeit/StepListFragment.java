package com.maximbravo.bakeit;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListFragment extends Fragment {

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
    public StepListFragment() {

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }
    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnImageClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_step_list, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.steps_list_view);

        StepListAdapter stepListAdapter = new StepListAdapter(getActivity(), StepListActivity.getCurrentRecipe());

        gridView.setAdapter(stepListAdapter);

        TextView title = (TextView) rootView.findViewById(R.id.recipe_title);
        title.setText(StepListActivity.getCurrentRecipe().getRecipeName());
        //add click listener
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onStepSelected(position);
            }
        });
        return rootView;
    }
}
