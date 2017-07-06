package com.maximbravo.bakeit;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListFragment extends Fragment {

    public static OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
    public StepListFragment() {

    }
    private int lastSelected;
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

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.steps_list_view);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        StepListAdapter stepListAdapter = new StepListAdapter(getActivity(), StepListActivity.getCurrentRecipe());

        recyclerView.setAdapter(stepListAdapter);


        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                int position = 2;
//                mCallback.onStepSelected(position-2);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("selectedPosition", lastSelected);
    }

    private void unselectAllBut(GridView gridView, int position) {
        int total = gridView.getChildCount();
        for (int i = 0; i < total; i++) {
            if (i == position) {
                gridView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.selected_background));
            } else {
                gridView.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.recipe_color));
            }
        }
    }

}
