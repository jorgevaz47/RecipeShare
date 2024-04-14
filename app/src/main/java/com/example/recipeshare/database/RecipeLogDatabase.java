package com.example.recipeshare.database;

import androidx.room.Database;

import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.Users;

@Database(entities = {RecipeLog.class, Users.class}, version = 2, exportSchema = false)

public class RecipeLogDatabase {
    public static final String USER_TABLE = "user_table";

    //Refer to Gymlog video 3 @ 16 min
}
