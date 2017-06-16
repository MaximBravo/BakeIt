package com.maximbravo.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListActivity extends AppCompatActivity implements
        StepListFragment.OnStepClickListener,
        StepFragment.OnFabSelected {
    private static int recipePosition;

    public static void setCurrentPosition(int currentPosition) {
        recipePosition = currentPosition;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        loadListFragment();

        Recipe current = BakingUtils.getRecipeAt(recipePosition);
        setTitle(current.getRecipeName());
        if(findViewById(R.id.tablet_master_list_layout) != null) {
            BakingUtils.twopanemode = true;
        } else {
            BakingUtils.twopanemode = false;
        }
    }


    private boolean first = true;
    public void onStepSelected(int position) {
        if (position >= 0) {
            if (BakingUtils.twopanemode) {
                BakingUtils.currentRecipe = BakingUtils.getRecipeAt(this.recipePosition);
                BakingUtils.currentStep = BakingUtils.currentRecipe.getSteps().get(position);
                StepFragment stepFragment = new StepFragment();


                //stepFragment.setPosition(BakingUtils.getRecipeAt(this.recipePosition), position);

                FragmentManager fragmentManager = getSupportFragmentManager();


                if (first) {
                    fragmentManager.beginTransaction()
                            .add(R.id.step_container, stepFragment)
                            .commit();
                    first = false;
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_container, stepFragment)
                            .commit();
                }
            } else {

                Intent intent = new Intent(this, StepActivity.class);
                intent.putExtra("stepNumber", position);
                BakingUtils.currentStep = BakingUtils.getRecipeAt(recipePosition).getSteps().get(position);
                intent.putExtra("recipeNumber", this.recipePosition);
                startActivity(intent);
            }
        }
    }

    private void loadListFragment() {
        StepListFragment stepListFragment = new StepListFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(Recipe.first) {
            fragmentManager.beginTransaction()
                    .add(R.id.steps_list_container, stepListFragment)
                    .commit();
            Recipe.first = false;
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_list_container, stepListFragment)
                    .commit();
        }
    }

    public static Recipe getCurrentRecipe(){
        return BakingUtils.getRecipeAt(recipePosition);
    }

    @Override
    public void onNextSelected() {
        //nothing
    }

    @Override
    public void onPrevSelected() {
        //nothing
    }
}
