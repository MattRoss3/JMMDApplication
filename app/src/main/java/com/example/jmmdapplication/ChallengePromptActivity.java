package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.databinding.ActivityChallengePromptBinding;

/**
 * Activity that displays a prompt for a challenge.
 * <p>
 * This activity shows information about a challenge and allows the user to start the challenge
 * in either multiple-choice or write-in formats. It also provides a way to navigate back to the
 * main user interface.
 * </p>
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 * @authors Jerrick Wallace, Dakota Fouch
 * @since 08/05/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */

public class ChallengePromptActivity extends AppCompatActivity {

    private static final String CHALLENGE_PROMPT_USER_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_USER_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_NAME";
    private static final String CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION";

    ActivityChallengePromptBinding binding;

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengePromptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getIntExtra(CHALLENGE_PROMPT_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_PROMPT_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_PROMPT_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION);

        binding.challengeScreenHeader.setText(challengeName);
        binding.challengeScreenDescription.setText(challengeDescription);

        // this button will start the challenge in multiple choice format
        binding.multipleChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChallengeScreenMultipleChoice.ChallengeMultipleChoiceIntentFactory(getApplicationContext(), userId, challengeId, challengeName, challengeDescription);
                startActivity(intent);
            }
        });

        // this button will start the challenge in a write in format
        binding.writeinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ChallengeScreenWritein.ChallengeWriteinIntentFactory(getApplicationContext(), userId, challengeId, challengeName, challengeDescription);
                startActivity(intent);
            }
        });

        // this back button will return to the main user interface activity
        binding.backButtonChallengePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * Creates an intent for starting the {@link ChallengePromptActivity}.
     *
     * @param context            The context to use for creating the intent.
     * @param userId             The ID of the user.
     * @param challengeId        The ID of the challenge.
     * @param challengeName      The name of the challenge.
     * @param challengeDescription The description of the challenge.
     * @return The intent to start {@link ChallengePromptActivity}.
     */
    public static Intent ChallengePromptIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengePromptActivity.class);
        intent.putExtra(CHALLENGE_PROMPT_USER_ID, userId);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}