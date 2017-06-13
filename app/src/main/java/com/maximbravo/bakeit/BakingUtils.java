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
        try {
            JSONArray recipesArray = new JSONArray(json);
            for(int i = 0; i < recipesArray.length(); i++) {
                JSONObject recipeJson = recipesArray.getJSONObject(i);
                String recipeName = recipeJson.getString("name");
                String recipeDescription = "Servings: " + recipeJson.getInt("servings");
                Recipe recipe = new Recipe(recipeName, recipeDescription);
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
