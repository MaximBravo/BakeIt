package com.maximbravo.bakeit;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Step> steps;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View stepItem;
        public ViewHolder(View view) {
            super(view);
            stepItem = view;
        }
    }

    public StepListAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.steps = recipe.getSteps();
    }

    private int n = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public StepListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView textView = (TextView) new TextView(context);
//        textView.setText("Made in onCreateViewHolder" + n);

        View view = getView(n);
        n++;
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View getView(int position) {
        View view;
        if (position == 0) {
            TextView title = (TextView) new TextView(context);
            title.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));

            title.setTextColor(Color.BLACK);
            title.setGravity(View.TEXT_ALIGNMENT_CENTER);
            title.setTextSize(30);
            title.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
            title.setElevation(2);
            title.setText(StepListActivity.getCurrentRecipe().getRecipeName());
            view = title;
        } else if (position == 1) {
            TextView ingredients = new TextView(context);
            ingredients.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT));
            ingredients.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
            ingredients.setElevation(2);
            ingredients.setTextColor(Color.BLACK);
            String ingridientsString = "Ingredients\n";
            ArrayList<Ingredient> currentIngredients = BakingUtils.currentRecipe.getIngredients();
            for(int i = 0; i < currentIngredients.size(); i++) {
                ingridientsString += "" + (i+1) + ") " + currentIngredients.get(i).toString() + "\n";
            }
            ingredients.setText(ingridientsString);
            view = ingredients;
        } else {
            view = steps.get(position-2).getView(context);
        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       holder.stepItem = getView(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return steps.size() + 2;
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if (position == 0) {
//            TextView title = (TextView) new TextView(context);
//            title.setTextColor(Color.BLACK);
//            title.setGravity(View.TEXT_ALIGNMENT_CENTER);
//            title.setTextSize(30);
//            title.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
//            title.setElevation(2);
//            title.setText(StepListActivity.getCurrentRecipe().getRecipeName());
//            return title;
//        } else if (position == 1) {
//            TextView ingredients = new TextView(context);
//            ingredients.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
//            ingredients.setElevation(2);
//            ingredients.setTextColor(Color.BLACK);
//            String ingridientsString = "Ingredients\n";
//            ArrayList<Ingredient> currentIngredients = BakingUtils.currentRecipe.getIngredients();
//            for(int i = 0; i < currentIngredients.size(); i++) {
//                ingridientsString += "" + (i+1) + ") " + currentIngredients.get(i).toString() + "\n";
//            }
//            ingredients.setText(ingridientsString);
//            return ingredients;
//        } else {
//            return steps.get(position-2).getView(context);
//        }
//    }
}
