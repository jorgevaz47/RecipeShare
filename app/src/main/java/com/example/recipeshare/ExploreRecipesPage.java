package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.recipeshare.databinding.ActivityMainBinding;

public class ExploreRecipesPage extends AppCompatActivity {

    private static final String EXPLORE_RECIPES_PAGE_USER_ID = "com.example.recipeshare.EXPLORE_RECIPES_PAGE_USER_ID";
    ActivityMainBinding binding;

    int loggedUserID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if(loggedUserID == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }
    }

    private void loginUser() {
        loggedUserID = getIntent().getIntExtra(EXPLORE_RECIPES_PAGE_USER_ID, -1);
    }

    static Intent exploreRecipesPageIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, ExploreRecipesPage.class);
        intent.putExtra(EXPLORE_RECIPES_PAGE_USER_ID, userID);
        return intent;
    }
}