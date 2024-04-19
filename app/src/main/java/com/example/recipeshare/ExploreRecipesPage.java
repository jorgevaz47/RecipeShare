package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.databinding.ActivityMainBinding;

public class ExploreRecipesPage extends AppCompatActivity {


    static String eName = "";
    static String eIngredients = "";
    static String eInstructions = "";
    static String eCreatedBy = "";
    boolean eIsFavorite = false;
    ActivityMainBinding binding;
    private RecipeLogRepository repository;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        backButton = findViewById(R.id.backButtonExRecipes);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void insertRecipeLogRecord(){
        RecipeLog log = new RecipeLog(eName,eIngredients,eInstructions,eCreatedBy,eIsFavorite);
        repository.insertRecipeLog(log);
    }

    static Intent exploreRecipesIntentFactory(Context context){
        return new Intent(context, ExploreRecipesPage.class);
    }


}