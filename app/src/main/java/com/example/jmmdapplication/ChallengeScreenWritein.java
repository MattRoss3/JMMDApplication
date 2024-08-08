package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.databinding.ActivityChallengeScreenWriteinBinding;
import com.example.jmmdapplication.viewmodel.AnswerViewModel;
import com.example.jmmdapplication.viewmodel.ProgressViewModel;
import com.example.jmmdapplication.viewmodel.QuestionViewModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This activity is the challenge section of the app. The user will be presented with a series of
 * language questions in the challenge they chose, using a write-in answer box. The user's progress
 * is kept track of. Once the user completes each question in the challenge, it is marked as
 * completed and the date is recorded.
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 * @since 08/05/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */
public class ChallengeScreenWritein extends AppCompatActivity {

    private static final String CHALLENGE_ACTIVITY_USER_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION";

    private ActivityChallengeScreenWriteinBinding binding;
    private ProgressViewModel progressViewModel;
    private QuestionViewModel questionViewModel;
    private AnswerViewModel answerViewModel;

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;
    private Progress progress;
    private String enteredAnswer;
    private int questionAttemptCounter = 0;
    private int amountOfQuestionsInChallenge = 0;
    private List<Question> challengeQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengeScreenWriteinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION);

        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        answerViewModel = new ViewModelProvider(this).get(AnswerViewModel.class);

        binding.challengeScreenHeader.setText(challengeName);
        binding.challengeScreenDescription.setText(challengeDescription);

        progressViewModel.getProgressByUserId(userId).observe(this, allProgress -> {
            progress = findOrCreateProgress(allProgress);
            loadQuestions();
        });

        binding.submitButton.setOnClickListener(view -> handleAnswerSubmission());

        binding.backButtonChallengeScreen.setOnClickListener(v -> {
            Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    private void loadQuestions() {
        questionViewModel.getQuestionsByChallengeId(challengeId).observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                challengeQuestions = questions;
                amountOfQuestionsInChallenge = questions.size();
                displayQuestion(challengeQuestions.get(questionAttemptCounter));
            }
        });
    }

    /**
     * Displays the current question in the challenge.
     *
     * @param question The current question to be displayed.
     */
    private void displayQuestion(Question question) {
        binding.questionText.setText(question.getQuestionText());
    }

    /**
     * Handles the submission of the user's entered answer.
     */
    private void handleAnswerSubmission() {
        enteredAnswer = binding.answerTextPrompt.getText().toString();
        Question currentQuestion = challengeQuestions.get(questionAttemptCounter);

        answerViewModel.getAnswersByQuestionId(currentQuestion.getQuestionId()).observe(this, questionWithAnswers -> {
            if (questionWithAnswers != null && !questionWithAnswers.isEmpty()) {
                Answer correctAnswer = findCorrectAnswer(questionWithAnswers.get(0).answers);

                if (correctAnswer == null) {
                    Toast.makeText(ChallengeScreenWritein.this, "Sorry, there is an issue with displaying this question. Returning to main screen.", Toast.LENGTH_SHORT).show();
                    navigateToMainUserInterface();
                    return;
                }

                if (enteredAnswer.equals(correctAnswer.getAnswerText())) {
                    Toast.makeText(ChallengeScreenWritein.this, "Congrats! That is the correct answer", Toast.LENGTH_SHORT).show();
                    progress.setLevel(progress.getLevel() + 1);
                } else {

                    Toast.makeText(ChallengeScreenWritein.this, "Sorry, the correct answer was " + correctAnswer.getAnswerText(), Toast.LENGTH_SHORT).show();
                }

                ++questionAttemptCounter;
                if (progress.getLevel() == amountOfQuestionsInChallenge) {
                    completeChallenge();
                } else if (questionAttemptCounter == amountOfQuestionsInChallenge) {
                    finishChallenge();
                } else {
                    displayQuestion(challengeQuestions.get(questionAttemptCounter));
                }
            }
        });
    }

    /**
     * Finds the correct Answer for the current question.
     *
     * @param questionAnswers A list of Answers under this Question.
     * @return An Answer object that is the correct answer for the current Question.
     */
    private Answer findCorrectAnswer(List<Answer> questionAnswers) {
        for (Answer currAnswer : questionAnswers) {
            if (currAnswer.isCorrect()) {
                return currAnswer;
            }
        }
        return null;
    }

    /**
     * Finds or creates the Progress object for the user's current challenge.
     *
     * @param allProgress The list of all progress objects for the user.
     * @return The Progress object for the current challenge.
     */
    private Progress findOrCreateProgress(List<Progress> allProgress) {
        if (allProgress == null || allProgress.isEmpty()) {
            return new Progress(userId, challengeId, "inProgress", LocalDateTime.of(1970, 1, 1, 1, 1, 1), 0);
        } else {
            for (Progress currProgress : allProgress) {
                if (currProgress.getChallengeId() == challengeId) {
                    return currProgress;
                }
            }
        }
        return new Progress(userId, challengeId, "inProgress", LocalDateTime.of(1970, 1, 1, 1, 1, 1), 0);
    }

    /**
     * Completes the challenge and updates the progress.
     */
    private void completeChallenge() {
        Toast.makeText(this, "Congratulations! You completed all questions in this challenge. Returning to Main Menu.", Toast.LENGTH_SHORT).show();
        progress.setStatus("isComplete");
        progress.setCompletionDate(LocalDateTime.now());
        navigateToMainUserInterface();
    }

    /**
     * Finishes the challenge but not completely.
     */
    private void finishChallenge() {
        Toast.makeText(this, "Challenge finished, but not 100%. Returning to main menu", Toast.LENGTH_SHORT).show();
        navigateToMainUserInterface();
    }

    /**
     * Navigates to the main user interface.
     */
    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    /**
     * Creates an intent for starting the {@link ChallengeScreenWritein} activity.
     *
     * @param context             The context to use for creating the intent.
     * @param userId              The ID of the user.
     * @param challengeId         The ID of the challenge.
     * @param challengeName       The name of the challenge.
     * @param challengeDescription The description of the challenge.
     * @return The intent to start {@link ChallengeScreenWritein}.
     */
    public static Intent ChallengeWriteinIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengeScreenWritein.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}
