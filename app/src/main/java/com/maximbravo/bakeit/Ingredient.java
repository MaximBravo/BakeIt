package com.maximbravo.bakeit;

/**
 * Created by Kids on 6/13/2017.
 */

class Ingredient {
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String toString(){
        return quantity + " " + measure + " of " + ingredient;
    }
}
