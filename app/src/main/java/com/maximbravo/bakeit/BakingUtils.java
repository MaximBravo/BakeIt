package com.maximbravo.bakeit;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Kids on 6/12/2017.
 */

public class BakingUtils {
    public static Recipe currentRecipe;
    public static boolean twopanemode;
    public static Context context;

    public static Step currentStep;
    public static ArrayList<Recipe> recipes = new ArrayList<>();
    public static ArrayList<Recipe> getRecipes(){
        return recipes;
    }

    public static void setCurrentRecipe(Recipe currentRecipe) {
        BakingUtils.currentRecipe = currentRecipe;



    }

    public static void fillDataFromJson(String json) {
        recipes.clear();
        try {
            JSONArray recipesArray = new JSONArray(json);
            for(int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeJson = recipesArray.getJSONObject(i);
                String recipeName = recipeJson.getString("name");
                String recipeDescription = "Servings: " + recipeJson.getInt("servings");
                String recipeImage = recipeJson.getString("image");
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
                    boolean video = true;
                    // Note: here is where we do the logic talked about in StepFragment
                    if(videoURL.length() == 0 || videoURL.equals("")) {
                        videoURL = stepJson.getString("thumbnailURL");
                        video = false;
                    }
                    Step currentStep = new Step(id, shortDescription, description, videoURL, video);
                    steps.add(currentStep);
                }

                Recipe recipe = new Recipe(recipeName, recipeDescription, ingredients, steps, recipeImage);
                recipes.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static String getJsonString(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

//        BakingUtils.context = context;
//        String json = null;
//        try {
//            InputStream inputStream = context.getAssets().open(fileName);
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return json;
    }

    public static Recipe getRecipeAt(int position) {
        if(recipes.size() == 0) {
            return new Recipe("ERROR", "nbc", null, null, null);
        }
        return recipes.get(position);
    }

    public static String getWidgetText() {
        if(recipes == null || recipes.size() == 0) {
            return "You havent opened the app in a while come have a look. So that ingredients can show here";
        } else {
            Recipe widgetRecipe = currentRecipe;
            if (widgetRecipe == null) {
                widgetRecipe = recipes.get(0);
            }
            String ingridientsString = widgetRecipe.getRecipeName() + "\n";
            ArrayList<Ingredient> currentIngredients = widgetRecipe.getIngredients();
            for (int i = 0; i < currentIngredients.size(); i++) {
                ingridientsString += "" + (i + 1) + ") " + currentIngredients.get(i).toString() + "\n";
            }
            return ingridientsString;
        }
    }
}
