package com.maximbravo.bakeit;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
        return steps.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            TextView title = (TextView) new TextView(context);
            title.setTextColor(Color.BLACK);
            title.setGravity(View.TEXT_ALIGNMENT_CENTER);
            title.setTextSize(30);
            title.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
            title.setElevation(2);
            title.setText(StepListActivity.getCurrentRecipe().getRecipeName());
            return title;
        } else if (position == 1) {
            TextView ingredients = new TextView(context);
            ingredients.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
            ingredients.setElevation(2);
            ingredients.setTextColor(Color.BLACK);
            String ingridientsString = "Ingredients\n";
            ArrayList<Ingredient> currentIngredients = BakingUtils.currentRecipe.getIngredients();
            for(int i = 0; i < currentIngredients.size(); i++) {
                ingridientsString += "" + (i+1) + ") " + currentIngredients.get(i).toString() + "\n";
            }
            ingredients.setText(ingridientsString);
            return ingredients;
        } else {
            return steps.get(position-2).getView(context);
        }
    }
}
