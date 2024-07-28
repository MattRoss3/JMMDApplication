package com.example.jmmdapplication;

import com.example.jmmdapplication.R;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainUserInterface extends AppCompatActivity {
    private ActivityMainUserInterfaceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_interface);
        binding=ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= signInPageActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=Settings_page.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.newChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=addnewChallenge.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button1MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button2MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button3MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button4MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button5MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button6MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button7MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button8MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button9MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.button10MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


    }
    static Intent intentFactory(Context context){
        return new Intent(context, MainUserInterface.class);
    }

}