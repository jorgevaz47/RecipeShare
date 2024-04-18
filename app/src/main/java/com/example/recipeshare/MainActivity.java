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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Keys used to access persistence storage
    private static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.recipeshare.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final String MAIN_ACTIVITY_PAGE_USER_ID = "com.example.recipeshare.MAIN_ACTIVITY_PAGE_USER_ID";
    // Tag used for debugging purposes
    public static final String TAG = "RECIPELOG";
    // Logged out state constant
    private static final int LOGGED_OUT = -1;
    // Initial state of logged in user. -1 means no user is logged in
    private int loggedUserID = -1;
    private RecipeLogRepository repository;
    ActivityMainBinding binding;


    /**
     * This method starts up the landing page and checks if there has been a user logged in or not
     * before displaying information. If no user is logged in, it takes the user to the LoginPage
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RecipeLogRepository.getRepository(getApplication());

        // Checks if there is a user logged in
        loginUser(savedInstanceState);

        // If no user is logged in, take them to the Login Page
        if(loggedUserID == -1){
            Intent intent = LoginPage.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Update the persistent data stored as a SharedPref with the logged-in user ID
        updateSharedPreference();

        addListeners();

    }

    /**
     * Adds on click listeners to each of the buttons in the main activity
     */
    private void addListeners() {
        // Adds a listener to the My Recipes button that takes them to that page
        binding.myRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MyRecipes.myRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        // Adds a listener to the Explore Recipes button that takes them to that page
        binding.exploreRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ExploreRecipesPage.exploreRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });

        // Adds a listener to the Favorite Recipes button that takes them to that page
        binding.favoriteRecipesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FavoriteRecipesPage.favoriteRecipesIntentFactory(MainActivity.this);
                startActivity(intent);
            }
        });
    }


    /**
     * This method attempts to check if either the shared preferences or the intent extra contain a
     * user ID that tell the page that someone is already signed-in
     * If a user is signed in, retrieve that user and apply user information to additional fields on the page
     * by calling populateFields
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    private void loginUser(Bundle savedInstanceState) {
        // Check shared preferences for logged in user
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        loggedUserID = sharedPreferences.getInt(getString(R.string.preference_userID_key), LOGGED_OUT);

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
        userObserver.observe(this, user -> {
            if(user != null){
                populateFields(user);
                invalidateOptionsMenu();
                showAdminButton(user.isAdmin());
            }
        });
    }

    /**
     * If the user is not an admin, it removes the admin button from the layout's space
     * @param isAdmin Tells us if the user is an admin or not
     */
    private void showAdminButton(boolean isAdmin){
        if(!isAdmin){
            binding.adminButton.setVisibility(View.GONE);
        }
    }

    /**
     * Populates the welcome field in the landing page to display the user's name
     * @param user User object with information
     */
    private void populateFields(User user) {
        TextView textView = findViewById(R.id.welcomeUserTitle);
        textView.setText(String.format(getString(R.string.welcome_user), user.getUsername()));
    }

    /**
     * Puts the logged in userID into the instance state for logging in use
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedUserID);
        updateSharedPreference();
    }

    /**
     * Adds a settings button/menu to the action bar
     * @param menu The options menu in which you place your items.
     * @return true always
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    /**
     * Tweaks settings for the options menu and adds an onClick listener to make it usable
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return true always
     */
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

    /**
     * Creates three buttons that a user can press when clicking on the Settings button on the taskbar
     * Allows the user to logout, delete account, or cancel actions
     */
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

    /**
     * This method allows the user to delete their account from the database
     */
    private void deleteAccount() {
        // TODO: Implement delete account
    }

    /**
     * Logs user out and reset information about logged in user
     * Sends user to login page after updating
     */
    private void logout() {
        loggedUserID = LOGGED_OUT;
        updateSharedPreference();
        startActivity(LoginPage.loginIntentFactory(getApplicationContext()));
    }

    /**
     * General method to update shared preferences
     */
    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userID_key), loggedUserID);
        sharedPrefEditor.apply();
    }

    /**
     * Method used to start the main activity from other pages
     * @param context Context of the application
     * @param userID UserID that is used as extra information
     * @return  The intent created with this information
     */
    static Intent mainActivityPageIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_PAGE_USER_ID, userID);
        return intent;
    }
}