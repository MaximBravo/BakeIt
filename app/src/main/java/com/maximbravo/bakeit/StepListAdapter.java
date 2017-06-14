package com.maximbravo.bakeit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Step> steps;
    public StepListAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.steps = recipe.getSteps();
    }
    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return steps.get(position).getView(context);
    }
}
