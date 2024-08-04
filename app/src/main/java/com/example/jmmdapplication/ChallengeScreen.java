package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityChallengeScreenBinding;


public class ChallengeScreen extends AppCompatActivity {

    private static final String CHALLENGE_ACTIVITY_USER_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID";

    private ActivityChallengeScreenBinding binding;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChallengeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);


    }

    public static Intent challengeIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, ChallengeScreen.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        return intent;
    }



}