package com.example.recipeshare.database.entities;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.recipeshare.database.RecipeLogDatabase;

import java.util.Objects;

@Entity(tableName = RecipeLogDatabase.RECIPE_LOG_TABLE)
public class RecipeLog {
    //Refer to GymLog video 3 @ 7 min

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String ingredients;
    private String instructions;
    private String createdBy;
    private boolean isFavorite;
    private int userID;

    public RecipeLog(String name, String ingredients, String instructions, String createdBy, boolean isFavorite, int userID) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.createdBy = createdBy;
        this.isFavorite = isFavorite;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return name + '\n' +
                ", ingredients: " + ingredients + '\n' +
                ", instructions: " + instructions + '\n' +
                ", createdBy: " + createdBy + '\n' +
                ", isFavorite: " + isFavorite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeLog recipeLog = (RecipeLog) o;
        return id == recipeLog.id && isFavorite == recipeLog.isFavorite && userID == recipeLog.userID && Objects.equals(name, recipeLog.name) && Objects.equals(ingredients, recipeLog.ingredients) && Objects.equals(instructions, recipeLog.instructions) && Objects.equals(createdBy, recipeLog.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, ingredients, instructions, createdBy, isFavorite, userID);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
