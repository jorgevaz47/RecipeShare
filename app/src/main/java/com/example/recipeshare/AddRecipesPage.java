package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.databinding.ActivityAddRecipesPageBinding;

public class AddRecipesPage extends AppCompatActivity {

    ActivityAddRecipesPageBinding binding;
    private RecipeLogRepository repository;

    static String mName = "";
    static String mIngredients = "";
    static String mInstructions = "";
    static String mCreatedBy = "";
    static boolean mIsFavorite = false;


    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = RecipeLogRepository.getRepository(getApplication());

        backButton = findViewById(R.id.backButtonAddPage);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton = findViewById(R.id.addRecipeSubmitButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertRecipeLogRecord();
                MyRecipes.updateDisplay();
            }
        });
    }

    /**
     * inserts inputs from user into our database
     */

    private void insertRecipeLogRecord(){
        RecipeLog log = new RecipeLog(mName,mIngredients,mInstructions,mCreatedBy,mIsFavorite);
        repository.insertRecipeLog(log);
    }

    /**
     * gets inputs to editTexts from user
     */
    private void getInformationFromDisplay(){
        mName = binding.nameInputEditText.getText().toString();
        mIngredients = binding.ingredientsInputEditText.getText().toString();
        mInstructions = binding.instructionsInputEditText.getText().toString();
        mCreatedBy = binding.createdByInputEditText.getText().toString();
    }
}