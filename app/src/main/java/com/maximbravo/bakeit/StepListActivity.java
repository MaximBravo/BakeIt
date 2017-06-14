package com.maximbravo.bakeit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Kids on 6/13/2017.
 */

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {
    private static int recipePosition;
    private boolean twopanemode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_list);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("recipePosition")) {
            int position = intent.getIntExtra("recipePosition", 0);
            this.recipePosition = position;
        }
    }


    public void onStepSelected(int position) {
        Toast.makeText(this, "We are toasting from StepList Activity", position).show();
        if(twopanemode) {

        } else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("stepNumber", position);
            intent.putExtra("recipeNumber", this.recipePosition);
            startActivity(intent);
        }
    }

    public static Recipe getCurrentRecipe(){
        return BakingUtils.getRecipeAt(recipePosition);
    }
}
