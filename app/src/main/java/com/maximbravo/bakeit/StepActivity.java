package com.maximbravo.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepActivity extends AppCompatActivity implements StepFragment.OnFabSelected {
    private boolean first = true;
    private boolean toolbarVisible = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE && !BakingUtils.twopanemode) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            toolbarVisible = false;
        } else {
            toolbarVisible = true;
        }
        setContentView(R.layout.activity_step);
        if(!toolbarVisible) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("stepNumber")) {
            int recipeNumber = intent.getIntExtra("recipeNumber", 1);
            int position = intent.getIntExtra("stepNumber", 0);
            addFragment(BakingUtils.getRecipeAt(recipeNumber), position);
        }


    }

    public void addFragment(Recipe recipe, int position){
        BakingUtils.currentRecipe = recipe;
        BakingUtils.currentStep = BakingUtils.currentRecipe.getSteps().get(position);

        StepFragment stepFragment = new StepFragment();
        
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(first) {
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepFragment)
                    .commit();
            first = false;
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.step_container, stepFragment)
                    .commit();
        }
    }
    @Override
    public void onNextSelected(Recipe recipe, int stepPosition) {
        if(stepPosition == recipe.getSteps().size() - 1) {
            addFragment(recipe, 0);
        } else {
            addFragment(recipe, stepPosition + 1);
        }
    }

    @Override
    public void onPrevSelected(Recipe recipe, int currentPosition) {
        if(currentPosition == 0) {
            addFragment(recipe, recipe.getSteps().size()-1);
        } else {
            addFragment(recipe, currentPosition - 1);
        }
    }
}
