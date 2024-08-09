package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.databinding.ActivityChallengeScreenMultipleChoiceBinding;
import com.example.jmmdapplication.viewmodel.AnswerViewModel;
import com.example.jmmdapplication.viewmodel.ProgressViewModel;
import com.example.jmmdapplication.viewmodel.QuestionViewModel;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Activity that displays a challenge and its associated questions to the user.
 * <p>
 * This activity handles the display of a challenge, its questions, and multiple-choice answers.
 * It also manages user interactions for answering questions and updating challenge progress.
 * </p>
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 * @since 07/20/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */
public class ChallengeScreenMultipleChoice extends AppCompatActivity {

    private static final String CHALLENGE_ACTIVITY_USER_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION";

    private ActivityChallengeScreenMultipleChoiceBinding binding;
    private ProgressViewModel progressViewModel;
    private QuestionViewModel questionViewModel;
    private AnswerViewModel answerViewModel;

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;
    private Progress progress;
    private RadioButton selectedAnswer;
    private int questionAttemptCounter = 0;
    private int amountOfQuestionsInChallenge = 0;
    private List<Answer> currentAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengeScreenMultipleChoiceBinding.inflate(getLayoutInflater());
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
                amountOfQuestionsInChallenge = questions.size();
                displayQuestion(questions.get(questionAttemptCounter));
            }
        });
    }

    /**
     * Displays the current question in the challenge. Binds the possible answers to radio buttons for
     * the user to choose.
     *
     * @param question The current question to be displayed.
     */
    private void displayQuestion(Question question) {
        // Clear previous selections
        binding.radioButton1.setChecked(false);
        binding.radioButton2.setChecked(false);
        binding.radioButton3.setChecked(false);
        binding.radioButton4.setChecked(false);

        answerViewModel.getAnswersByQuestionId(question.getQuestionId()).observe(this, questionWithAnswers -> {
            if (questionWithAnswers != null && !questionWithAnswers.isEmpty()) {
                currentAnswers = questionWithAnswers.get(0).answers;
                binding.questionText.setText(question.getQuestionText());

                // Use utility method to set text and OnClickListener for RadioButtons within CardViews
                setupRadioButton(binding.cardView1, R.id.radioButton1, currentAnswers.get(0).getAnswerText());
                setupRadioButton(binding.cardView2, R.id.radioButton2, currentAnswers.get(1).getAnswerText());
                setupRadioButton(binding.cardView3, R.id.radioButton3, currentAnswers.get(2).getAnswerText());
                setupRadioButton(binding.cardView4, R.id.radioButton4, currentAnswers.get(3).getAnswerText());
            }
        });
    }

    private void setupRadioButton(CardView cardView, int radioButtonId, String text) {
        RadioButton radioButton = cardView.findViewById(radioButtonId);
        radioButton.setText(text);
        radioButton.setOnClickListener(view -> {
            // Deselect all radio buttons
            binding.radioButton1.setChecked(false);
            binding.radioButton2.setChecked(false);
            binding.radioButton3.setChecked(false);
            binding.radioButton4.setChecked(false);

            // Select the clicked radio button
            radioButton.setChecked(true);

            // Set the selected answer
            selectedAnswer = (RadioButton) view;
        });
    }


    /**
     * Handles the submission of the user's selected answer.
     */
    private void handleAnswerSubmission() {
        if (selectedAnswer != null) {
            boolean isCorrect = false;
            Answer correctAnswer = null;
            for (Answer answer : currentAnswers) {
                if (answer.isCorrect()) {
                    correctAnswer = answer;
                }
                if (selectedAnswer.getText().equals(answer.getAnswerText()) && answer.isCorrect()) {
                    isCorrect = true;
                }
            }

            if (isCorrect) {
                Toast.makeText(this, "Congrats! That is the correct answer", Toast.LENGTH_SHORT).show();
                progress.setLevel(progress.getLevel() + 1);
            } else {
                Toast.makeText(this, "Sorry, the correct answer was " + (correctAnswer != null ? correctAnswer.getAnswerText() : "unknown"), Toast.LENGTH_SHORT).show();
            }

            ++questionAttemptCounter;
            questionViewModel.getQuestionsByChallengeId(challengeId).observe(this, questions -> {
                if (questions != null) {
                    proceedToNextQuestionOrFinish(questions);
                }
            });
        }
    }

    /**
     * Proceeds to the next question or finishes the challenge based on the user's progress.
     *
     * @param questions The list of questions in the challenge.
     */
    private void proceedToNextQuestionOrFinish(List<Question> questions) {
        if (progress.getLevel() == amountOfQuestionsInChallenge) {
            Toast.makeText(this, "Congratulations! You completed all questions in this challenge. Returning to Main Menu.", Toast.LENGTH_SHORT).show();
            progress.setStatus("isComplete");
            progress.setCompletionDate(LocalDateTime.now());
            navigateToMainUserInterface();
        } else if (questionAttemptCounter == amountOfQuestionsInChallenge) {
            Toast.makeText(this, "Challenge finished, but not 100%. Returning to main menu", Toast.LENGTH_SHORT).show();
            navigateToMainUserInterface();
        } else {
            displayQuestion(questions.get(questionAttemptCounter));
        }
    }

    /**
     * Navigates to the main user interface.
     */
    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
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
     * Creates an intent for starting the {@link ChallengeScreenMultipleChoice} activity.
     *
     * @param context             The context to use for creating the intent.
     * @param userId              The ID of the user.
     * @param challengeId         The ID of the challenge.
     * @param challengeName       The name of the challenge.
     * @param challengeDescription The description of the challenge.
     * @return The intent to start {@link ChallengeScreenMultipleChoice}.
     */
    public static Intent ChallengeMultipleChoiceIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengeScreenMultipleChoice.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}
