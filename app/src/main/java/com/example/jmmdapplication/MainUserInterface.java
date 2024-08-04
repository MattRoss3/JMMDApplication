package com.example.jmmdapplication;

import com.example.jmmdapplication.Database.Relations.ChallengeWithDetails;
import com.example.jmmdapplication.Database.Relations.QuestionWithAnswer;
import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
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


    private UserWithDetails user;
    private DatabaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_user_interface);
        com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding binding = ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.newChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainUserInterface.this, ChallengeScreen.class);
                startActivity(intent);
            }
        });

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManager.clearUserSession(MainUserInterface.this);
                Intent intent = new Intent(MainUserInterface.this, SignInPageActivity.class);
                startActivity(intent);
                finish();
            }
        });

        repository = new DatabaseRepository(getApplication());

        int userId = SessionManager.getUserSession(this);

        UserWithDetails userWithDetails = repository.getUserWithDetails(userId);

        if (userWithDetails != null) {
            User user = userWithDetails.user;

            String welcomeMessage = getString(R.string.welcome_message, user.getUsername());
            binding.mainUserHeader.setText(welcomeMessage);

//            List<ChallengeWithDetails> challenges = userWithDetails.challengeWithDetails;
//
//            for (ChallengeWithDetails challengeDetails : challenges) {
//                Challenge challenge = challengeDetails.challenge;
//                List<QuestionWithAnswer> questionWithAnswers = challengeDetails.questionWithAnswers;
//
//                for (QuestionWithAnswer questionWithAnswer : questionWithAnswers) {
//                    Question question = questionWithAnswer.question;
//                    Answer answer = questionWithAnswer.answer;
//
//                    // Process the data as needed
//                    // For example:
//                    Log.d("ExampleActivity", "Question: " + question.getQuestionText());
//                    if (answer != null) {
//                        Log.d("ExampleActivity", "Answer: " + answer.getAnswerText());
//                    }
//                }
//            }

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

    static Intent MainUserInterfaceIntentFactory(Context context){
        return new Intent(context, MainUserInterface.class);
    }



}