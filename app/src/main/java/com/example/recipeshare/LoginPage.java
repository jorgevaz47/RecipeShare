package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityLoginPageBinding;

public class LoginPage extends AppCompatActivity {

    ActivityLoginPageBinding binding;

    private RecipeLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
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

    private void verifyUser() {
        String username = binding.editUsername.getText().toString();
        String password = binding.editPassword.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                if(password.equals(user.getPassword())){
                    startActivity(MainActivity.mainActivityPageIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Not a valid username", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginPage.class);
    }
}