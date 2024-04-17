package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.databinding.ActivityExploreRecipesPageBinding;

public class ExploreRecipesPage extends AppCompatActivity {

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_recipes_page);

        backButton = findViewById(R.id.backButtonExRecipes);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    static Intent exploreRecipesIntentFactory(Context context){
        return new Intent(context, ExploreRecipesPage.class);
    }
}