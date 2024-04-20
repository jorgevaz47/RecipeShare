package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipeshare.database.RecipeLogRepository;
import com.example.recipeshare.database.entities.MyRecipes;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;
import com.example.recipeshare.databinding.ActivityAddRecipesPageBinding;

public class AddRecipesPage extends AppCompatActivity {

    private static final String ADD_RECIPES_PAGE_USER_ID = "com.example.recipeshare.ADD_RECIPES_PAGE_USER_ID";
    ActivityAddRecipesPageBinding binding;
    private RecipeLogRepository repository;

    static String mName = "";
    static String mIngredients = "";
    static String mInstructions = "";
    static String mCreatedBy = "";
    static int mUserID = -1;


    Button backButton;
    Button addButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRecipesPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = RecipeLogRepository.getRepository(getApplication());

        backButton = findViewById(R.id.backButtonAddPage);

        createByField(getIntent().getIntExtra(ADD_RECIPES_PAGE_USER_ID, -1));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addButton = findViewById(R.id.addRecipeSubmitButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertRecipeLogRecord();
                onBackPressed();
            }
        });
    }

    /**
     * inserts inputs from user into our database
     */

    private void insertRecipeLogRecord(){
        mUserID = getIntent().getIntExtra(ADD_RECIPES_PAGE_USER_ID, -1);

        if(mUserID == -1){
            Log.i("ERROR_ADD_RECIPES_PAGE", "Critical error in add recipes page. userID not correctly passed through the Intent factories");
        }

        RecipeLog log = new RecipeLog(mName,mIngredients,mInstructions,mCreatedBy,mUserID);

        LiveData<MyRecipes> myRecipesObserver = repository.getMyRecipeRecord(mUserID);

        myRecipesObserver.observe(this, myRecipes -> {
            if(myRecipes == null || myRecipes.getMyRecipes().isEmpty()){
                MyRecipes recipe = new MyRecipes(mUserID);
                recipe.getMyRecipes().add(log);
            } else{
                myRecipes.getMyRecipes().add(log);
            }
            repository.insertMyRecipe(myRecipes);
        });

        repository.insertRecipeLog(log);
    }

    /**
     *
     */


    public void createByField(int userID) {
        TextView textView = findViewById(R.id.createdByTextView);
        LiveData<User> userObserver = repository.getUserByUserID(userID);
        userObserver.observe(this, user -> {
            if(user != null){
                textView.setText(String.format(getString(R.string.createdBystring), user.getUsername()));
            }
        });

    }

    /**
     * gets inputs to editTexts from user
     */
    private void getInformationFromDisplay(){
        mName = binding.nameInputEditText.getText().toString();
        mIngredients = binding.ingredientsInputEditText.getText().toString();
        mInstructions = binding.instructionsInputEditText.getText().toString();
        mCreatedBy = binding.createdByTextView.getText().toString();
    }

    static Intent addRecipesIntentFactory(Context context, int userID){
        Intent intent = new Intent(context, AddRecipesPage.class);
        intent.putExtra(ADD_RECIPES_PAGE_USER_ID, userID);
        return intent;
    }
}