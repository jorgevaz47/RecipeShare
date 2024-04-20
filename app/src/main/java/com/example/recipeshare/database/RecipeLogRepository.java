package com.example.recipeshare.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.recipeshare.MainActivity;
import com.example.recipeshare.database.entities.MyRecipes;
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
    private final UserDAO userDAO;
    private final MyRecipesDAO myRecipesDAO;
    private ArrayList<RecipeLog> allLogs;
    private ArrayList<User> allUserLogs;
    private static RecipeLogRepository repository;


    private RecipeLogRepository(Application application) {
        RecipeLogDatabase db = RecipeLogDatabase.getDatabase(application);
        this.recipeLogDAO = db.recipeLogDAO();
        this.userDAO = db.userDAO();
        this.myRecipesDAO = db.myRecipesDAO();
        this.allLogs = (ArrayList<RecipeLog>) this.recipeLogDAO.getAllRecords();
        this.allUserLogs = (ArrayList<User>) this.userDAO.getAllRecords();
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

    public ArrayList<User> getAllUserLogs() {
        Future<ArrayList<User>> future = RecipeLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
                        return (ArrayList<User>) userDAO.getAllRecords();
                    }
                });
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all UserLogs in repository");
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

    public void insertMyRecipe(MyRecipes myRecipes){
        RecipeLogDatabase.databaseWriteExecutor.execute(() -> {
            myRecipesDAO.insert(myRecipes);
        });
    }

    public LiveData<MyRecipes> getMyRecipeRecord(int userID){
        return myRecipesDAO.getMyRecipeRecord(userID);
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserID(int loggedUserID) {
        return userDAO.getUserByUserID(loggedUserID);
    }

    public User getUserByUserNameNotLive(String mUserName) {
        return userDAO.getUserByUserNameNotLive(mUserName);
    }

    public void deleteUser(int id){
        RecipeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.deleteUser(id);
        });
    }

    public void deleteRecipe(String name){
        RecipeLogDatabase.databaseWriteExecutor.execute(() ->
        {
            recipeLogDAO.deleteRecipes(name);
        });
    }
    public void updateMyReicpes(MyRecipes myRecipes){
        RecipeLogDatabase.databaseWriteExecutor.execute(() -> {
            myRecipesDAO.updateMyRecipes(myRecipes);
        });
    }
}
