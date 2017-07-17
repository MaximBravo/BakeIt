package com.maximbravo.bakeit;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.SparseArray;
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
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.steps_list_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        StepListAdapter stepListAdapter = new StepListAdapter(getActivity(), StepListActivity.getCurrentRecipe());
        recyclerView.setAdapter(stepListAdapter);
        recyclerView.setLayoutManager(layoutManager);

//        Bundle b = getArguments();
//        String s = b.getString("a");
//        System.out.println(s);
        return rootView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            Parcelable listState = savedInstanceState.getParcelable(selectKey);
            if (listState != null) {
                layoutManager.onRestoreInstanceState(listState);
//                ((SavedState) mListState).
            }
        }
    }

    private final String selectKey = "selectedPosition";
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        BakingUtils.lastSelected = recyclerView.computeVerticalScrollOffset();
//        outState.putInt(selectKey, BakingUtils.lastSelected);
        // Save list state
        Parcelable listState = layoutManager.onSaveInstanceState();
        outState.putParcelable(selectKey, listState);
    }






}
