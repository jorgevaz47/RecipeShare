package com.example.recipeshare.database;

import android.app.Application;
import android.util.Log;

import com.example.recipeshare.MainActivity;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//TODO: Refer to Gymlog video 3 @ 40 min
public class RecipeLogRepository {
    private final RecipeLogDAO recipeLogDAO;
    private ArrayList<RecipeLog> allLogs;
    private final UserDAO userDAO;

    private RecipeLogRepository(Application application) {
        RecipeLogDatabase db = RecipeLogDatabase.getDatabase(application);
        this.recipeLogDAO = db.recipeLogDAO();
        this.allLogs = this.recipeLogDAO.getAllRecords();
    }

    public ArrayList<RecipeLog> getAllLogs() {
        Future<ArrayList<RecipeLog>> future = RecipeLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<RecipeLog>>() {
                    @Override
                    public ArrayList<RecipeLog> call() throws Exception {
                        return recipeLogDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Recipelogs in repository");
        }
        return null;
    }

    public void insertRecipeLog(RecipeLog recipeLog){
        RecipeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            recipeLogDAO.insert(recipeLog);
        });
    }

    public void insertUser(User... user) {
        RecipeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

}
