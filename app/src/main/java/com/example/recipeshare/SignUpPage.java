package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityAddRecipesPageBinding;
import com.example.recipeshare.databinding.ActivitySignUpPageBinding;

public class SignUpPage extends AppCompatActivity {

    String mUserName = "";
    String mPassword = "";

    private RecipeLogRepository repository;

    Button backButton;

    ActivitySignUpPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = RecipeLogRepository.getRepository(getApplication());

        backButton = findViewById(R.id.backButtonSignPage);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                createUserLogRecord();
                onBackPressed();
            }
        });
    }

    private void getInformationFromDisplay(){
        mUserName = binding.signUpUsername.getText().toString();
        mPassword = binding.signUpPassword.getText().toString();
    }

    private void createUserLogRecord() {
        LiveData<User> userObserver = repository.getUserByUserName(mUserName);
        userObserver.observe(this, user -> {
            if (user != null) {
                System.out.println("Username not available, please try again.");
            } else {
                Toast.makeText(SignUpPage.this, "Account created, please log in.", Toast.LENGTH_SHORT).show();
                User newUser = new User(mUserName, mPassword);
                repository.insertUser(newUser);
            }
        });
    }

    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignUpPage.class);
    }
}