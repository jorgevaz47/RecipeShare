package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recipeshare.database.RecipeLogRepository;
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
    }

    private void getInformationFromDisplay(){
        mUserName = binding.signUpUsername.getText().toString();
        mPassword = binding.signUpPassword.getText().toString();
    }

    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignUpPage.class);
    }
}