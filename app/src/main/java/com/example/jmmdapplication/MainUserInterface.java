package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.util.SessionManager;


public class MainUserInterface extends AppCompatActivity {

    private ActivityMainUserInterfaceBinding binding;
    private DatabaseRepository repository;
    private UserWithDetails userWithDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = new DatabaseRepository(getApplication());
        int userId = SessionManager.getUserSession(this);
        userWithDetails = repository.getUserWithDetails(userId);

        userWithDetails.challengeWithDetails.get(0).challenge.setAssigned(false);





        setupUI();
        setupListeners();
    }

    private void setupUI() {
        if (userWithDetails != null) {
            User user = userWithDetails.user;
            String welcomeMessage = getString(R.string.welcome_message, user.getUsername());
            binding.mainUserHeader.setText(welcomeMessage);

            if (user.isAdmin()) {
                binding.editUserButton.setVisibility(View.VISIBLE);
            } else {
                binding.editUserButton.setVisibility(View.GONE);
            }
        }
    }

    private void setupListeners() {
        binding.newChallengeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainUserInterface.this, ChallengeScreen.class);
            startActivity(intent);
        });

        binding.logoutButton.setOnClickListener(v -> {
            SessionManager.clearUserSession(MainUserInterface.this);
            Intent intent = new Intent(MainUserInterface.this, SignInPageActivity.class);
            startActivity(intent);
            finish();
        });

        binding.editUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainUserInterface.this, AdminEditActivity.class);
            startActivity(intent);
        });
    }

    public static Intent MainUserInterfaceIntentFactory(Context context) {
        return new Intent(context, MainUserInterface.class);
    }
}
