package com.maximbravo.bakeit;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Kids on 6/12/2017.
 */

public class BakingUtils {

    public static ArrayList<Recipe> recipes = new ArrayList<>();
    public static ArrayList<Recipe> getRecipes(Context context){
        String json = getJsonString(context, "baking.json");
        fillDataFromJson(json);
        return recipes;
    }

    private static void fillDataFromJson(String json) {
        recipes.clear();
        try {
            JSONArray recipesArray = new JSONArray(json);
            for(int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeJson = recipesArray.getJSONObject(i);
                String recipeName = recipeJson.getString("name");
                String recipeDescription = "Servings: " + recipeJson.getInt("servings");

                JSONArray ingredientsJson = recipeJson.getJSONArray("ingredients");
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                for(int k = 0; k < ingredientsJson.length(); k++) {
                    JSONObject ingredientJson = ingredientsJson.getJSONObject(k);
                    int quantity = ingredientJson.getInt("quantity");
                    String measure = ingredientJson.getString("measure");
                    String ingredient = ingredientJson.getString("ingredient");
                    Ingredient currentIngredient = new Ingredient(quantity, measure, ingredient);
                    ingredients.add(currentIngredient);
                }

                JSONArray stepsJson = recipeJson.getJSONArray("steps");
                ArrayList<Step> steps = new ArrayList<>();
                for (int k = 0; k < stepsJson.length(); k++) {
                    JSONObject stepJson = stepsJson.getJSONObject(k);
                    int id = stepJson.getInt("id");
                    String shortDescription = stepJson.getString("shortDescription");
                    String description = stepJson.getString("description");
                    String videoURL = stepJson.getString("videoURL");
                    Step currentStep = new Step(id, shortDescription, description, videoURL);
                    steps.add(currentStep);
                }

                Recipe recipe = new Recipe(recipeName, recipeDescription, ingredients, steps);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getJsonString(Context context, String fileName) {
        String json = null;
        try {
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
