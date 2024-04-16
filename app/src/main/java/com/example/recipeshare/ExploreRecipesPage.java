package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipeshare.databinding.ActivityMainBinding;

public class ExploreRecipesPage extends AppCompatActivity {

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
        // TODO: Create login method
    }
}