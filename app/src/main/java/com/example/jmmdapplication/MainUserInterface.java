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

import java.util.List;

/**
 * This application will act as a quick and easy language learning app.
 *
 * @authors Jerrick Wallace, Mohamed Othman, Matthew Ross, Dakota Fouch
 * @since 07/20/2024
 * CST 338 Software Design with Dr. C
 * wk05: Project 2
 */

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

        binding.button1MainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainUserInterface.this, ChallengeScreen.class);
                startActivity(intent);
            }
        });


//---------------------------------Example of how to get the user with details---------------------------------
        // this is an example of how to get the user with details including challenges, questions, answers, and progress from the UserWithDetails object

        // first initialize the repository
        repository = new DatabaseRepository(getApplication());

        // get the user id from the session
        int userId = SessionManager.getUserSession(this);

        // get the user with details
        UserWithDetails userWithDetails = repository.getUserWithDetails(userId);

        // if the user is not null, then we can extract the user, challenges, questions, answers, and progress from userWithDetails
        if (userWithDetails != null) {

            // get the user object
            User user = userWithDetails.user;

            // get the challenges with details
            List<ChallengeWithDetails> challenges = userWithDetails.challengeWithDetails;

            // loop through the challenges and get the questions and answers
            for (ChallengeWithDetails challengeDetails : challenges) {
                Challenge challenge = challengeDetails.challenge;
                List<QuestionWithAnswer> questionWithAnswers = challengeDetails.questionWithAnswers;

                for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
                    Question question = questionWithAnswer.question;
                    Answer answer = questionWithAnswer.answer;

                    // Process the data as needed
                    // For example:
                    Log.d("ExampleActivity", "Question: " + question.getQuestionText());
                    if (answer != null) {
                        Log.d("ExampleActivity", "Answer: " + answer.getAnswerText());
                    }
                }
            }

            // get the progress Object
            List<Progress> progresses = userWithDetails.progress;
            for (Progress progress : progresses) {
                // Process the data as needed
                // For example:
                Log.d("ExampleActivity", "Progress: " + progress.getCompletionDate());
            }
        }
    }
//---------------------------------Example of how to get the user with details---------------------------------

    public static Intent MainUserInterfaceIntentFactory(Context context) {
        return new Intent(context, MainUserInterface.class);
    }
}