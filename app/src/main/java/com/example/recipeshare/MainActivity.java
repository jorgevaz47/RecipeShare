package com.example.recipeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int LOGGED_OUT = -1;
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.recipeshare.SAVED_INSTANCE_STATE_USERID_KEY";
    ActivityMainBinding binding;
    private RecipeLogRepository repository;
    private static final String MAIN_ACTIVITY_PAGE_USER_ID = "com.example.recipeshare.MAIN_ACTIVITY_PAGE_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.recipeshare.SHARED_PREFERENCE_USERID_KEY";
    static final String SHARED_PREFERENCE_USERID_VALUE = "com.example.recipeshare.SHARED_PREFERENCE_USERID_VALUE";
    public static final String TAG = "DAC_RECIPELOG";
    String mName = "";
    String mIngredients = "";
    String mInstructions = "";
    String mCreatedBy = "";
    boolean mIsFavorite = false;

    //TODO: Add login information.. refer video 7 @ 22min
    private int loggedUserID = -1;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        loginUser(savedInstanceState);

        if(loggedUserID == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        } else{
            // TODO: This is causing errors so far even with the implemented login. When retrieving a user with LiveData, it still says the user variable is null
//            TextView textView = findViewById(R.id.welcomeUserTitle);
//            textView.setText(String.format(getString(R.string.welcome_user), user.getUsername()));
        }

        binding.myRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyRecipes.myRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        binding.exploreRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExploreRecipesPage.exploreRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        binding.favoriteRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FavoriteRecipesPage.favoriteRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminPage.adminIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });
        //todo: working on video 5, wondering if all this data should be on main or diff class
    }

    //TODO: should this be on main? we are not displaying recipes here...Display should be on explore, myRecipes, favRecipes. video 6
    private void insertRecipeLogRecords(){
        RecipeLog log = new RecipeLog(mName, mIngredients, mInstructions, mCreatedBy, mIsFavorite, loggedUserID);
        repository.insertRecipeLog(log);
    }

    private void loginUser(Bundle savedInstanceState) {
        // Check shared preferences for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(SHARED_PREFERENCE_USERID_KEY)){
            loggedUserID = sharedPreferences.getInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedUserID == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedUserID = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedUserID == LOGGED_OUT){
            loggedUserID = getIntent().getIntExtra(MAIN_ACTIVITY_PAGE_USER_ID, LOGGED_OUT);
        }
        if(loggedUserID == LOGGED_OUT){
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserID(loggedUserID);
        userObserver.observe(this, newUser -> {
            this.user = newUser;
            if(user != null){
                invalidateOptionsMenu();
            } else{
//                logout();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedUserID);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, loggedUserID);
        sharedPrefEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.settingsItem);
        item.setVisible(true);
        item.setTitle("Settings");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showSettingsDialog();
                return false;
            }
        });
        return true;
    }

    private void showSettingsDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Settings");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Delete Account", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAccount();
            }
        });

        alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void deleteAccount() {
    }

    private void logout() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(SHARED_PREFERENCE_USERID_KEY, LOGGED_OUT);
        sharedPrefEditor.apply();

        getIntent().putExtra(MAIN_ACTIVITY_PAGE_USER_ID, LOGGED_OUT);

        startActivity(LoginPage.loginIntentFactory(getApplicationContext()));
    }

    static Intent mainActivityPageIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_PAGE_USER_ID, userID);
        return intent;
    }
}