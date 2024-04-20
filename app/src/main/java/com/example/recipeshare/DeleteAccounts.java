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
import com.example.recipeshare.databinding.ActivityDeleteAccountsBinding;

import java.util.ArrayList;

public class DeleteAccounts extends AppCompatActivity {

    ActivityDeleteAccountsBinding binding;
    private RecipeLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAccountsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = RecipeLogRepository.getRepository(getApplication());
        binding.deleteAccountsDisplay.setMovementMethod(new ScrollingMovementMethod());

        deleteUsersDisplay();

        binding.backButtonDeleteAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void deleteUsersDisplay(){
        ArrayList<User> allUserLogs = repository.getAllUserLogs();
        if (allUserLogs.isEmpty()){
            binding.deleteAccountsDisplay.setText("");
        }
        StringBuilder sb = new StringBuilder();
        for (User log : allUserLogs){
            sb.append(log);
        }
        binding.deleteAccountsDisplay.setText(sb.toString());
    }

    static Intent deleteAccountsIntentFactory(Context context){
        return new Intent(context, DeleteAccounts.class);
    }
}