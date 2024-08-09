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

/**
 * Activity that allows users to add a new challenge.
 * <p>
 * This activity provides an interface for users to input details for a new challenge.
 * It includes a button to navigate back to the main user interface.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 */
public class AddNewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;
    private ChallengeViewModel challengeViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private Challenge selectedChallenge = null;
    private int userId;

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the UI by inflating the layout and setting up click listeners.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}. <b>Note:</b> Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddnewChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        challengeViewModel = new ViewModelProvider(this).get(ChallengeViewModel.class);
        userChallengeViewModel = new ViewModelProvider(this).get(UserChallengeViewModel.class);

        userId = SessionManager.getUserSession(this);

        binding.backButton.setOnClickListener(view -> navigateToMainUserInterface());
        binding.searchButton.setOnClickListener(view -> searchChallenge());
        binding.challengeButton.setOnClickListener(view -> addChallenge());
    }

    /**
     * Navigates to the main user interface.
     */
    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    /**
     * Searches for a challenge based on the entered language.
     */
    private void searchChallenge() {
        String language = binding.enterLanguageBar.getText().toString();
        if (!language.isEmpty()) {
            challengeViewModel.getAllChallenges().observe(this, challenges -> {
                selectedChallenge = findChallengeByLanguage(challenges, language);
                if (selectedChallenge != null) {
                    String challengeDetails = selectedChallenge.getCategory() + "\n" + selectedChallenge.getDescription();
                    binding.challengeButton.setText(challengeDetails);
                    binding.challengeButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(this, "Language not Found", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter a language", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Finds a challenge by language from a list of challenges.
     *
     * @param challenges The list of challenges.
     * @param language   The language to search for.
     * @return The challenge if found, otherwise null.
     */
    private Challenge findChallengeByLanguage(List<Challenge> challenges, String language) {
        for (Challenge challenge : challenges) {
            if (challenge.getCategory().equalsIgnoreCase(language)) {
                return challenge;
            }
        }
        return null;
    }

    /**
     * Adds the selected challenge if it is not already assigned to the user.
     */
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

    /**
     * Factory method to create an {@link Intent} for starting {@link AddNewChallenge}.
     *
     * @param context The context from which the intent will be started.
     * @return An {@link Intent} that can be used to start {@link AddNewChallenge}.
     */
    public static Intent intentFactory(Context context) {
        return new Intent(context, AddNewChallenge.class);
    }
}
