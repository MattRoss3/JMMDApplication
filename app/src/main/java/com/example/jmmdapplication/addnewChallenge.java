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

import com.example.jmmdapplication.databinding.ActivityAddnewChallengeBinding;

public class addnewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_challenge);
        binding=ActivityAddnewChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=MainUserInterface.intentFactory(getApplicationContext());
                startActivity(intent);
            }

        });
    }
    static Intent intentFactory(Context context){
        return new Intent(context, addnewChallenge.class);
    }
}