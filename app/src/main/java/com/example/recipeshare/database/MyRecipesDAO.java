package com.example.recipeshare.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeshare.database.entities.MyRecipes;

@Dao
public interface MyRecipesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyRecipes myRecipes);

    @Update
    void updateMyRecipes(MyRecipes myRecipes);

    @Query("SELECT * FROM " + RecipeLogDatabase.MY_RECIPES_TABLE + " WHERE userID == :userID")
    LiveData<MyRecipes> getMyRecipeRecord(int userID);

    @Delete
    void delete(MyRecipes myRecipes);

    @Query("DELETE from " + RecipeLogDatabase.MY_RECIPES_TABLE)
    void deleteAll();
}
