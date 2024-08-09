package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.databinding.ActivityAddnewChallengeBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.ChallengeViewModel;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;

import java.util.List;

public class AddNewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;
    private ChallengeViewModel challengeViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private Challenge selectedChallenge = null;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddnewChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        challengeViewModel = new ViewModelProvider(this).get(ChallengeViewModel.class);
        userChallengeViewModel = new ViewModelProvider(this).get(UserChallengeViewModel.class);

        userId = SessionManager.getUserSession(this);

        binding.backButton.setOnClickListener(view -> navigateToMainUserInterface());
        binding.SearchButton.setOnClickListener(view -> searchChallenge());
        binding.button1.setOnClickListener(view -> addChallenge());
    }

    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    private void searchChallenge() {
        String language = binding.enterLanguageBar.getText().toString();
        if (!language.isEmpty()) {
            challengeViewModel.getAllChallenges().observe(this, challenges -> {
                selectedChallenge = findChallengeByLanguage(challenges, language);
                if (selectedChallenge != null) {
                    String challengeDetails = selectedChallenge.getCategory() + "\n" + selectedChallenge.getDescription();
                    binding.button1.setText(challengeDetails); // Display the challenge details inside the button
                    binding.button1.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, "Language not Found", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter a language", Toast.LENGTH_SHORT).show();
        }
    }

    private Challenge findChallengeByLanguage(List<Challenge> challenges, String language) {
        for (Challenge challenge : challenges) {
            if (challenge.getCategory().equalsIgnoreCase(language)) {
                return challenge;
            }
        }
        return null;
    }

    private void addChallenge() {
        if (selectedChallenge != null) {
            userChallengeViewModel.getChallengesAssignedToUser(userId).observe(this, userChallenges -> {
                boolean isAlreadyAssigned = userChallenges.challenges.stream()
                        .anyMatch(challenge -> challenge.getChallengeId() == selectedChallenge.getChallengeId());

                if (isAlreadyAssigned) {
                    Toast.makeText(this, "Challenge already assigned", Toast.LENGTH_SHORT).show();
                } else {
                    userChallengeViewModel.assignChallengeToUser(userId, selectedChallenge.getChallengeId());
                    Toast.makeText(this, "Challenge added successfully", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No Challenge to add", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent intentFactory(Context context) {
        return new Intent(context, AddNewChallenge.class);
    }
}
