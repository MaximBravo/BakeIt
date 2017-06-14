package com.maximbravo.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepActivity extends AppCompatActivity implements StepFragment.OnFabSelected {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("stepNumber")) {
            int recipeNumber = intent.getIntExtra("recipeNumber", 1);
            int position = intent.getIntExtra("stepNumber", 0);
            addFragment(BakingUtils.getRecipeAt(recipeNumber), position, true);
        }


    }

    public void addFragment(Recipe recipe, int position, boolean first){
        StepFragment stepFragment = new StepFragment();

        stepFragment.setPosition(recipe, position);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(first) {
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        }
    }
    @Override
    public void onNextSelected(Recipe recipe, int stepPosition) {
        if(stepPosition == recipe.getSteps().size() - 1) {
            addFragment(recipe, 0, false);
        } else {
            addFragment(recipe, stepPosition + 1, false);
        }
    }

    @Override
    public void onPrevSelected(Recipe recipe, int currentPosition) {
        if(currentPosition == 0) {
            addFragment(recipe, recipe.getSteps().size()-1, false);
        } else {
            addFragment(recipe, currentPosition - 1, false);
        }
    }
}
