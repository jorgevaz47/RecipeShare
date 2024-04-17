package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    private RecipeLogRepository repository;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!verifyUser()){
                    Toast.makeText(LoginPage.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = MainActivity.mainActivityPageIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SignUpPage.signUpIntentFactory(LoginPage.this);
                startActivity(intent);
            }
        });
    }

    private boolean verifyUser() {
        String username = binding.editUsername.getText().toString();
        String password = binding.editPassword.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            return false;
        }
        user = repository.getUserByUserName(username);

        if(user != null){
            return password.equals(user.getPassword());
        }
        return false;
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginPage.class);
    }
}