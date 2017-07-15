package com.maximbravo.bakeit;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.name;

/**
 * Created by Kids on 6/12/2017.
 */

public class Recipe {
    public static boolean first = true;
    private String recipeName;
    private String recipeDescription;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private String image;

    //dummy testing purposes
    public Recipe(String name, String description, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, String image) {
        this.recipeName = name;
        this.recipeDescription = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.image = image;
    }

    //returns a view that represents one Recipe
    public View getView(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        LinearLayout recipeView = (LinearLayout) layoutInflater.inflate(R.layout.recipe_list_item, null);

        TextView recipeNameView = (TextView) recipeView.getChildAt(0);
        TextView recipeDescriptionView = (TextView) recipeView.getChildAt(1);

        recipeNameView.setText(recipeName);
        recipeDescriptionView.setText(recipeDescription);

        return recipeView;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public String getImage() {return image;}
}
