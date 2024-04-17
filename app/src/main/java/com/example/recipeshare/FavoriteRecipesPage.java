package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FavoriteRecipesPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipes_page);
    }

    static Intent favoriteRecipesIntentFactory(Context context){
        return new Intent(context, FavoriteRecipesPage.class);
    }
}