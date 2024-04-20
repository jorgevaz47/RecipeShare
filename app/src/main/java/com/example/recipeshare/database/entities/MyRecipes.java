package com.example.recipeshare.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.recipeshare.database.RecipeLogDatabase;
import com.example.recipeshare.database.converters.RecipeLogListConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * This class defines the MyRecipesPage object that is accessed through a userID key
 * and holds a List of Recipes. The userID and list of Recipes can not be reassigned
 * after initialization.
 */
@Entity(tableName = RecipeLogDatabase.MY_RECIPES_TABLE)
public class MyRecipes {

    @PrimaryKey
    private final int userID;

    @TypeConverters(RecipeLogListConverter.class)
    private List<RecipeLog> myRecipes;

    public MyRecipes(int userID){
        this.userID = userID;
        myRecipes = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyRecipes myRecipes1 = (MyRecipes) o;
        return userID == myRecipes1.userID && Objects.equals(myRecipes, myRecipes1.myRecipes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, myRecipes);
    }

    public int getUserID() {
        return userID;
    }

    public List<RecipeLog> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(List<RecipeLog> myRecipes) {
        this.myRecipes = myRecipes;
    }
}
