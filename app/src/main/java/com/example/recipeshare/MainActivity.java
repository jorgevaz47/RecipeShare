package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private RecipeLogRepository repository;
    private static final String MAIN_ACTIVITY_PAGE_USER_ID = "com.example.recipeshare.MAIN_ACTIVITY_PAGE_USER_ID";
    public static final String TAG = "DAC_RECIPELOG";
    String mName = "";
    String mIngredients = "";
    String mInstructions = "";
    String mCreatedBy = "";
    boolean mIsFavorite = false;

    //TODO: Add login information.. refer video 7 @ 22min
    int loggedUserID = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        loginUser();

        if(loggedUserID == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
        //todo: working on video 5, wondering if all this data should be on main or diff class
    }

    //TODO: should this be on main? we are not displaying recipes here...Display should be on explore, myRecipes, favRecipes. video 6
    private void insertRecipeLogRecords(){
        RecipeLog log = new RecipeLog(mName, mIngredients, mInstructions, mCreatedBy, mIsFavorite, loggedUserID);
        repository.insertRecipeLog(log);
    }

    private void loginUser() {
        loggedUserID = getIntent().getIntExtra(MAIN_ACTIVITY_PAGE_USER_ID, -1);
    }

    static Intent mainActivityPageIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, ExploreRecipesPage.class);
        intent.putExtra(MAIN_ACTIVITY_PAGE_USER_ID, userID);
        return intent;
    }
}