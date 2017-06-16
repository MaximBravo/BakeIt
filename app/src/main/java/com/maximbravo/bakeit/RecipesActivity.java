package com.maximbravo.bakeit;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        initializeGridView();

        if(findViewById(R.id.tablet_master_list_layout) != null) {
            BakingUtils.twopanemode = true;
        } else {
            BakingUtils.twopanemode = false;
        }
    }

    private void initializeGridView() {
        //Get gridview for main recipes screen
        GridView recipes = (GridView) findViewById(R.id.recipes_list);
        if(BakingUtils.twopanemode) {
            recipes.setNumColumns(2);
        }
        recipes.setAdapter(new RecipeAdapter(this, BakingUtils.getRecipes(this)));

        recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(RecipesActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecipesActivity.this, StepListActivity.class);
                BakingUtils.currentRecipe = BakingUtils.getRecipeAt(position);
                StepListActivity.setCurrentPosition(position);
                startActivity(intent);

            }
        });
    }

//    private ArrayList<Recipe> getDummyDataSet() {
//        //dummy Recipe data
//        ArrayList<Recipe> dummyRecipesData = new ArrayList<>();
//        dummyRecipesData.add(new Recipe("Cookies", "Servings: 20"));
//        dummyRecipesData.add(new Recipe("Brownies", "Servings: 10"));
//        dummyRecipesData.add(new Recipe("Apple Pie", "Servings: 1"));
//
//        return dummyRecipesData;
//    }
}
