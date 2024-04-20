package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.databinding.ActivityAdminPageBinding;

public class AdminPage extends AppCompatActivity {

    Button backButton;

    ActivityAdminPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backButton = findViewById(R.id.backButtonAdminPage);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.deleteAccountsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteAccounts.deleteAccountsIntentFactory(AdminPage.this);
                startActivity(intent);
            }
        });

        binding.deleteRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = DeleteRecipes.deleteRecipesIntentFactory(AdminPage.this);
                startActivity(intent);
            }
        });
    }

    static Intent adminIntentFactory(Context context){
        return new Intent(context, AdminPage.class);
    }
}