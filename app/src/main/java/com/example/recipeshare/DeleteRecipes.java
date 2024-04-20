package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityDeleteRecipesBinding;

import java.util.ArrayList;

public class DeleteRecipes extends AppCompatActivity {

    ActivityDeleteRecipesBinding binding;

    private RecipeLogRepository repository;

    String mName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = RecipeLogRepository.getRepository(getApplication());
        binding.deleteRecipesDisplay.setMovementMethod(new ScrollingMovementMethod());

        deleteRecipesDisplay();

        binding.backButtonDeleteRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.deleteRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                deleteAccount();
                deleteRecipesDisplay();
            }
        });
    }

    private void deleteRecipesDisplay(){
        ArrayList<RecipeLog> allLogs = repository.getAllLogs();
        if (allLogs.isEmpty()){
            binding.deleteRecipesDisplay.setText("");
        }
        StringBuilder sb = new StringBuilder();
        for (RecipeLog log : allLogs){
            sb.append(log);
        }
        binding.deleteRecipesDisplay.setText(sb.toString());
    }

    private void getInformationFromDisplay(){
        mName = binding.deleteRecipesEditText.getText().toString();
    }

    private void deleteAccount(){
        repository.deleteRecipe(mName);
    }

    static Intent deleteRecipesIntentFactory(Context context){
        return new Intent(context, DeleteRecipes.class);
    }

}