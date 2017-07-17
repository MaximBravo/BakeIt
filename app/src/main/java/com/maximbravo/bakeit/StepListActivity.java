package com.maximbravo.bakeit;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.maximbravo.bakeit.BakingUtils.context;

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
    private int mAppWidgetId = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if(savedInstanceState == null) {
            StepListFragment stepListFragment = new StepListFragment();
//            Bundle b = new Bundle();
//            b.putString("a", "Herro");
//            stepListFragment.setArguments(b);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.steps_list_container, stepListFragment)
                    .commit();
        }

        Recipe current = BakingUtils.getRecipeAt(recipePosition);
        setTitle(current.getRecipeName());
        if(findViewById(R.id.tablet_master_list_layout) != null) {
            BakingUtils.twopanemode = true;
        } else {
            BakingUtils.twopanemode = false;
        }


    }

    private StepFragment prevFragment;
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
                    prevFragment = stepFragment;
                } else {
                    prevFragment.getActivity().findViewById(R.id.loadingOverlay).setVisibility(View.VISIBLE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_container, stepFragment)
                            .commit();
                    prevFragment = stepFragment;
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
