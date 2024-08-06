package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.databinding.ActivityChallengePromptBinding;

/**
 * This activity will ask the user if they want the challenge to be multiple choice or using a write-in answer box.
 * The user will be presented with a button to start the multiple choice activity and a button to start the write-in activity.
 *
 * @authors Dakota Fouch
 * @since 08/05/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */

public class ChallengePromptActivity extends AppCompatActivity {

    private static final String CHALLENGE_PROMPT_USER_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_USER_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_NAME";
    private static final String CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION";

    private ActivityChallengePromptBinding binding;

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

        binding.multipleChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ChallengePromptActivity.this, ChallengeScreenMultipleChoice.class);
//                startActivity(intent);

                Intent intent = ChallengeScreenMultipleChoice.ChallengeMultipleChoiceIntentFactory(getApplicationContext(), userId, challengeId, challengeName, challengeDescription);
                startActivity(intent);
            }
        });

        binding.writeinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ChallengePromptActivity.this, ChallengeScreenWritein.class);
//                startActivity(intent);

                Intent intent = ChallengeScreenWritein.ChallengeWriteinIntentFactory(getApplicationContext(), userId, challengeId, challengeName, challengeDescription);
                startActivity(intent);
            }
        });

        binding.backButtonChallengePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent ChallengePromptIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengePromptActivity.class);
        intent.putExtra(CHALLENGE_PROMPT_USER_ID, userId);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}