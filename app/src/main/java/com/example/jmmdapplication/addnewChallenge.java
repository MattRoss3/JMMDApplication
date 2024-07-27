package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityAddnewChallengeBinding;

public class addnewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddnewChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}