package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.databinding.ActivitySignInPageBinding;


public class signInPageActivity extends AppCompatActivity {

    private ActivitySignInPageBinding binding;
    private static final int USER_ID = 0; // Use 0 as the hardcoded user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButtonSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChallengeScreen.challengeIntentFactory(getApplicationContext(), USER_ID);
                startActivity(intent);
            }
        });


    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, signInPageActivity.class);
    }
}