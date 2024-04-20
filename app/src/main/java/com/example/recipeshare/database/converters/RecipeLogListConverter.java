package com.example.recipeshare.database.converters;

import androidx.room.TypeConverter;

import com.example.recipeshare.database.entities.RecipeLog;

import java.util.ArrayList;
import java.util.List;

public class RecipeLogListConverter {
    @TypeConverter
    public String fromList(List<RecipeLog> recipes){
        StringBuilder sb = new StringBuilder();
        for(RecipeLog recipe : recipes){
            sb.append(recipe.getId()).append(",").append(recipe.getName()).append(",")
                    .append(recipe.getIngredients()).append(",").append(recipe.getInstructions())
                    .append(",").append(recipe.getUserID()).append(",").append(recipe.getCreatedBy()).append(";");
        }
        return sb.toString();
    }
    @TypeConverter
    public List<RecipeLog> toList(String recipesString){
        List<RecipeLog> recipes = new ArrayList<>();
        String[] recipeArray = recipesString.split(";");
        for(String recipeString : recipeArray){
            String[] fields = recipeString.split(",");
            if(fields.length == 6){
                String recipename = fields[1];
                String ingredients = fields[2];
                String instructions = fields[3];
                int userID = Integer.parseInt(fields[4]);
                String createdBy = fields[5];
                recipes.add(new RecipeLog(recipename, ingredients, instructions, createdBy, userID));
            }
        }
        return recipes;
    }
}
