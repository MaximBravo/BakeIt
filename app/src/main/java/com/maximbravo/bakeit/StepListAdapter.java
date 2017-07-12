package com.maximbravo.bakeit;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.security.AccessController;
import java.util.ArrayList;

import static com.maximbravo.bakeit.BakingUtils.currentRecipe;
import static com.maximbravo.bakeit.BakingUtils.currentStep;
import static java.security.AccessController.getContext;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {
    private Context context;
    private ArrayList<Step> steps;
    public static int lastSelected;


    public static class StepViewHolder extends RecyclerView.ViewHolder {
        View stepItem;
        TextView stepNumber;
        TextView stepName;
        TextView stepDescription;
        public StepViewHolder(View view) {
            super(view);
            stepItem = view;
            stepNumber = (TextView) view.findViewById(R.id.step_id);
            stepName =(TextView) view.findViewById(R.id.step_short_description);
            stepDescription = (TextView) view.findViewById(R.id.step_description);
            if(BakingUtils.currentStep != null) {
                if (Integer.parseInt(stepNumber.getText().toString()) == BakingUtils.currentStep.getId()) {
                    stepItem.setBackgroundResource(R.color.selected_background);
                } else {
                    stepItem.setBackgroundResource(R.color.recipe_color);
                }
            }
            stepItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (stepNumber.getVisibility() == View.VISIBLE) {
                        Log.e("SDJFLKSDJF", "Hello from position: " + stepNumber.getText().toString());
                        StepListAdapter.lastSelected = Integer.parseInt(stepNumber.getText().toString());
                        StepListFragment.mCallback.onStepSelected(Integer.parseInt(stepNumber.getText().toString()));
                        stepItem.setBackgroundResource((R.color.selected_background));
                        BakingUtils.currentStep = currentRecipe.getSteps().get(Integer.parseInt(stepNumber.getText().toString()));
                    } else {
                        stepItem.setBackgroundResource((R.color.recipe_color));
                        // BakingUtils.currentStep = null;
                    }
                }
            });
        }


    }

    public StepListAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.steps = recipe.getSteps();
    }

    private int n = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        TextView textView = (TextView) new TextView(context);
//        textView.setText("Made in onCreateViewHolder" + n);

        Toast.makeText(context, "n onCreateViewHolder: " + n, Toast.LENGTH_SHORT).show();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_list_item, parent, false);
        StepViewHolder stepViewHolder = new StepViewHolder(view);
        n++;
        return stepViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View getView(int position) {
        View view;
//        if (position == 0) {
//            TextView title = (TextView) new TextView(context);
//
//            title.setTextColor(Color.BLACK);
//            title.setGravity(View.TEXT_ALIGNMENT_CENTER);
//            title.setTextSize(30);
//            title.setBackgroundColor(context.getResources().getColor(R.color.recipe_color));
//            title.setElevation(2);
//            title.setText(StepListActivity.getCurrentRecipe().getRecipeName());
//            view = title;
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
//            view = ingredients;
//        } else {
            view = steps.get(position).getView(context);
//        }

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Toast.makeText(context, "position bindViewHolder: " + position, Toast.LENGTH_SHORT).show();
        int color = R.color.recipe_color;
        if(position == lastSelected) {
            color = R.color.selected_background;
        }
        if(position == 0) {
            holder.stepName.setText(BakingUtils.currentRecipe.getRecipeName());
            String ingridientsString = "Ingredients\n";
            ArrayList<Ingredient> currentIngredients = BakingUtils.currentRecipe.getIngredients();
            for(int i = 0; i < currentIngredients.size(); i++) {
                ingridientsString += "" + (i+1) + ") " + currentIngredients.get(i).toString() + "\n";
            }
            holder.stepDescription.setText(ingridientsString);
            holder.stepNumber.setVisibility(View.GONE);
            holder.stepItem.setBackgroundResource(R.color.recipe_color);
        } else {
            Step currentStep = steps.get(position-1);
            if (holder != null &&
                    holder.stepItem != null &&
                    holder.stepNumber != null &&
                    holder.stepName != null &&
                    holder.stepDescription != null) {
                holder.stepNumber.setVisibility(View.VISIBLE);
                holder.stepNumber.setText(String.valueOf(currentStep.getId()));
                holder.stepName.setText(currentStep.getShortDescription());
                holder.stepDescription.setText(currentStep.getDescription());
                holder.stepItem.setBackgroundResource(R.color.recipe_color);
            }
            //holder.stepItem = currentStep.getView(context);
        }


    }



    @Override
    public int getItemCount() {
        return steps.size() + 1;
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
