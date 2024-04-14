package com.example.recipeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.recipeshare.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    public static final String TAG = "DAC_RECIPELOG";
    String mName = "";
    String mIngredients = "";
    String mInstructions = "";
    String mCreatedBy = "";
    boolean mIsFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}