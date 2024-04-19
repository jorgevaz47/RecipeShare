package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.databinding.ActivityExploreRecipesPageBinding;
import com.example.recipeshare.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class ExploreRecipesPage extends AppCompatActivity {


    static String eName = "";
    static String eIngredients = "";
    static String eInstructions = "";
    static String eCreatedBy = "";
    boolean eIsFavorite = false;
    ActivityExploreRecipesPageBinding binding;
    private RecipeLogRepository repository;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExploreRecipesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        binding.exRecipesDisplay.setMovementMethod(new ScrollingMovementMethod());

        exploreRecipesDisplay();

        backButton = findViewById(R.id.backButtonExRecipes);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void exploreRecipesDisplay(){
        ArrayList<RecipeLog> allLogs = repository.getAllLogs();
        if (allLogs.isEmpty()){
            binding.exRecipesDisplay.setText("");
        }
        StringBuilder sb = new StringBuilder();
        for (RecipeLog log : allLogs){
            sb.append(log);
        }
        binding.exRecipesDisplay.setText(sb.toString());
    }

    static Intent exploreRecipesIntentFactory(Context context){
        return new Intent(context, ExploreRecipesPage.class);
    }


}