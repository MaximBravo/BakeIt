package com.maximbravo.bakeit;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.attr.name;

/**
 * Created by Kids on 6/12/2017.
 */

public class Recipe {
    private String recipeName;
    private String recipeDescription;

    //dummy testing purposes
    public Recipe(String name, String description) {
        this.recipeName = name;
        this.recipeDescription = description;
    }

    //returns a view that represents one Recipe
    public View getView(Context context){


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout recipeView = (LinearLayout) layoutInflater.inflate(R.layout.recipe_list_item, null);

        TextView recipeNameView = (TextView) recipeView.getChildAt(0);
        TextView recipeDescriptionView = (TextView) recipeView.getChildAt(1);

        recipeNameView.setText(recipeName);
        recipeDescriptionView.setText(recipeDescription);

//        recipeView.addView(recipeNameView, 0);
//        recipeView.addView(recipeDescriptionView, 1);


//        TextView recipeNameView = new TextView(context);
//        TextView recipeDescriptionView = new TextView(context);
//
//        recipeNameView.setText(recipeName);
//        recipeDescriptionView.setText(recipeDescription);
//
//        LinearLayout recipeView = new LinearLayout(context);
//        recipeView.setOrientation(LinearLayout.VERTICAL);
//
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        layoutParams.setMargins(30, 30, 30, 30);
//
//        recipeView.addView(recipeNameView);
//        recipeView.addView(recipeDescriptionView);
//        recipeView.setLayoutParams(layoutParams);
//        recipeView.setBackgroundColor(Color.DKGRAY);
//        ViewCompat.setElevation(recipeView, 10);

        return recipeView;
    }
}
