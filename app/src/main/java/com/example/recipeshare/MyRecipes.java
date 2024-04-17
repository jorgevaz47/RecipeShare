package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyRecipes extends AppCompatActivity {

    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_recipes);

        backButton = findViewById(R.id.backButtonMyRecipes);

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
                Intent intent = new Intent(MyRecipes.this, AddRecipesPage.class);
                startActivity(intent);
            }
        });
    }

    static Intent myRecipesIntentFactory(Context context){
        return new Intent(context, MyRecipes.class);
    }
}