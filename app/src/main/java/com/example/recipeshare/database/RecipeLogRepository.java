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
    private static final String TAG = "com.example.recipeshare.database.RECIPE_LOG_REPOSITORY";
    private final RecipeLogDAO recipeLogDAO;
    private ArrayList<RecipeLog> allLogs;
    private static RecipeLogRepository repository;

    private final UserDAO userDAO;

    private RecipeLogRepository(Application application) {
        RecipeLogDatabase db = RecipeLogDatabase.getDatabase(application);
        this.recipeLogDAO = db.recipeLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<RecipeLog>) this.recipeLogDAO.getAllRecords();
    }

    public static RecipeLogRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<RecipeLogRepository> future = RecipeLogDatabase.databaseWriteExecutor.submit(
                new Callable<RecipeLogRepository>() {
                    @Override
                    public RecipeLogRepository call() throws Exception {
                        return new RecipeLogRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting RecipeLogRepository, thread error.");
        }
        return null;
    }

    public ArrayList<RecipeLog> getAllLogs() {
        Future<ArrayList<RecipeLog>> future = RecipeLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<RecipeLog>>() {
                    @Override
                    public ArrayList<RecipeLog> call() throws Exception {
                        return (ArrayList<RecipeLog>) recipeLogDAO.getAllRecords();
                    }
                });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all RecipeLogs in repository");
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

    public User getUserByUserName(String username) {
        Future<User> future = RecipeLogDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDAO.getUserByUserName(username);
                    }
                });
        try{
            future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(RecipeLogRepository.TAG, "Problem retrieving user by username from repository");
        }
        return null;
    }
}
