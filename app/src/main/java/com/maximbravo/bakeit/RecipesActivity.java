package com.maximbravo.bakeit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

import static android.R.attr.x;
import static com.maximbravo.bakeit.BakingUtils.context;

public class RecipesActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        new LoadJson().execute(null, null, null);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initializeGridView();

        if(findViewById(R.id.tablet_master_list_layout) != null) {
            BakingUtils.twopanemode = true;
        } else {
            BakingUtils.twopanemode = false;
        }



    }

    private class LoadJson extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                BakingUtils.fillDataFromJson(BakingUtils.getJsonString("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private void initializeGridView() {
        //Get gridview for main recipes screen
        GridView recipes = (GridView) findViewById(R.id.recipes_list);
        if(BakingUtils.twopanemode) {
            recipes.setNumColumns(2);
        }
        recipes.setAdapter(new RecipeAdapter(this, BakingUtils.getRecipes()));

        recipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(RecipesActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RecipesActivity.this, StepListActivity.class);
                BakingUtils.currentRecipe = BakingUtils.getRecipeAt(position);
                Intent updateWidget = new Intent(RecipesActivity.this, BakeItWidget.class); // Widget.class is your widget class
                updateWidget.setAction("update_widget");
                PendingIntent pending = PendingIntent.getBroadcast(RecipesActivity.this, 0, updateWidget, PendingIntent.FLAG_CANCEL_CURRENT);
                try {
                    pending.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
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
