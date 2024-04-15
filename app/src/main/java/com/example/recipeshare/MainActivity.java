package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private RecipeLogRepository repository;

    public static final String TAG = "DAC_RECIPELOG";
    String mName = "";
    String mIngredients = "";
    String mInstructions = "";
    String mCreatedBy = "";
    boolean mIsFavorite = false;

    //TODO: Add login information.. refer video 7 @ 22min
    int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());
        //todo: working on video 5, wondering if all this data should be on main or diff class
    }

    //TODO: should this be on main? we are not displaying recipes here...Display should be on explore, myRecipes, favRecipes. video 6
    private void insertRecipeLogRecords(){
        RecipeLog log = new RecipeLog(mName, mIngredients, mInstructions, mCreatedBy, mIsFavorite, loggedInUserId);
        repository.insertRecipeLog(log);
    }
}