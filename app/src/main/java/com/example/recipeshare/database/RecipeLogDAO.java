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
    ArrayList<RecipeLog> getAllRecords();

}
