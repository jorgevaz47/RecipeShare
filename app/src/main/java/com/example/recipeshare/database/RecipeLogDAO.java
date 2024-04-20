package com.example.recipeshare.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipeshare.database.entities.RecipeLog;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface RecipeLogDAO {
    //Refer to Gymlog video 3 @ 19:30 min

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeLog recipeLog);

    @Query("SELECT * FROM " + RecipeLogDatabase.RECIPE_LOG_TABLE)
    List<RecipeLog> getAllRecords();

    @Query("DELETE from " + RecipeLogDatabase.RECIPE_LOG_TABLE)
    void deleteAll();

    @Query("DELETE from " + RecipeLogDatabase.RECIPE_LOG_TABLE + " WHERE name == :name")
    void deleteRecipes(String name);
}
