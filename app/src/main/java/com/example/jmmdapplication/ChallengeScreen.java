package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityChallengeScreenBinding;

public class ChallengeScreen extends AppCompatActivity {
    private ActivityChallengeScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_screen);
        binding=ActivityChallengeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}