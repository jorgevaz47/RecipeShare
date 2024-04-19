package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.databinding.ActivityMyRecipesBinding;

import java.util.Locale;

public class MyRecipes extends AppCompatActivity {

    private static final String MY_RECIPES_PAGE_USER_ID = "com.example.recipeshare.MY_RECIPES_PAGE_USER_ID";

    static ActivityMyRecipesBinding binding;
    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Intent intent = AddRecipesPage.addRecipesIntentFactory(MyRecipes.this, getIntent().getIntExtra(MY_RECIPES_PAGE_USER_ID, -1));
                startActivity(intent);
            }
        });
    }



    public static void updateDisplay(){
        String currentInfo = binding.myRecipesDisplay.getText().toString();
        String newDisplay = String.format(Locale.US,"Name: %s%nIngredients: %s%nInstructions: %s%nCreated By: %s%n=-=-=-=%n%s",AddRecipesPage.mName,AddRecipesPage.mIngredients,AddRecipesPage.mInstructions,AddRecipesPage.mCreatedBy,currentInfo);

        binding.myRecipesDisplay.setText(newDisplay);
    }

    static Intent myRecipesIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MyRecipes.class);
        intent.putExtra(MY_RECIPES_PAGE_USER_ID, userID);
        return intent;
    }
}