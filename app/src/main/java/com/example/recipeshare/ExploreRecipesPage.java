package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ExploreRecipesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_recipes_page);
    }

    static Intent exploreRecipesIntentFactory(Context context){
        return new Intent(context, ExploreRecipesPage.class);
    }
}