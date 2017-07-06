package com.maximbravo.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addFragment(BakingUtils.currentStep.getId());



    }

    public void addFragment(int position){
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
    public void onNextSelected() {
        if(BakingUtils.currentStep.getId() == BakingUtils.currentRecipe.getSteps().size() - 1) {
            addFragment(0);
        } else {
            addFragment(BakingUtils.currentStep.getId() + 1);
        }
    }

    @Override
    public void onPrevSelected() {
        if(BakingUtils.currentStep.getId() == 0) {
            addFragment(BakingUtils.currentRecipe.getSteps().size() - 1);
        } else {
            addFragment(BakingUtils.currentStep.getId() - 1);
        }
    }
}
