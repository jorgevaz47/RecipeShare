package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.MyRecipes;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityMyRecipesBinding;

import java.util.Locale;

public class MyRecipesPage extends AppCompatActivity {

    private static final String MY_RECIPES_PAGE_USER_ID = "com.example.recipeshare.MY_RECIPES_PAGE_USER_ID";

    static ActivityMyRecipesBinding binding;
    private RecipeLogRepository repository;
    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.i("LOOK FOR ME", "Int intent passed: " + getIntent().getIntExtra(MY_RECIPES_PAGE_USER_ID, -1));
        repository = RecipeLogRepository.getRepository(getApplication());

        backButton = findViewById(R.id.backButtonMyRecipes);

        binding.myRecipesDisplay.setMovementMethod(new ScrollingMovementMethod());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton = findViewById(R.id.addRecipesButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AddRecipesPage.addRecipesIntentFactory(MyRecipesPage.this, getIntent().getIntExtra(MY_RECIPES_PAGE_USER_ID, -1));
                startActivity(intent);
            }
        });

        updateDisplay();
    }

    public void updateDisplay(){
        StringBuilder sb = new StringBuilder();

        int userID = getIntent().getIntExtra(MY_RECIPES_PAGE_USER_ID, -1);

        // TODO: Remove hard coded admin userId
        LiveData<MyRecipes> myRecipesObserver = repository.getMyRecipeRecord(userID);
        Log.i("LOOK FOR ME", "I have the myReicpesObject");

        myRecipesObserver.observe(this, myRecipes -> {
            if(myRecipes != null && !myRecipes.getMyRecipes().isEmpty()) {
                for(RecipeLog log : myRecipes.getMyRecipes()){
                    String newDisplay = String.format(Locale.US,"Name: %s%nIngredients: %s%n" +
                            "Instructions: %s%nCreated By: %s%n=-=-=-=",log.getName(),
                            log.getIngredients(),log.getInstructions(),
                            log.getCreatedBy());
                    sb.append(newDisplay);
                }
                binding.myRecipesDisplay.setText(sb.toString());
            }
        });

        if(sb.toString().isEmpty()){
            binding.myRecipesDisplay.setText(R.string.no_recipes_time_to_get_cooking);
        }

    }

    static Intent myRecipesIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MyRecipesPage.class);
        intent.putExtra(MY_RECIPES_PAGE_USER_ID, userID);
        return intent;
    }
}