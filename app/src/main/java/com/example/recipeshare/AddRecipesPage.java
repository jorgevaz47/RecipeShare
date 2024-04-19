package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.databinding.ActivityAddRecipesPageBinding;

public class AddRecipesPage extends AppCompatActivity {

    public ActivityAddRecipesPageBinding binding;

    static String mName = "";
    static String mIngredients = "";
    static String mInstructions = "";
    static String mCreatedBy = "";
    int mIsFavorite = 0;

    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipes_page);

        backButton = findViewById(R.id.backButtonAddPage);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //TODO:possible issue below
        addButton = findViewById(R.id.addRecipeSubmitButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyRecipes.updateDisplay();
            }
        });
    }


    private void getInformationFromDisplay(){
        mName = binding.nameInputEditText.getText().toString();
        mIngredients = binding.ingredientsInputEditText.getText().toString();
        mInstructions = binding.instructionsInputEditText.getText().toString();
        mCreatedBy = binding.createdByInputEditText.getText().toString();
    }
}