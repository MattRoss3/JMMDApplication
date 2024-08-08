package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.databinding.ActivityChallengeScreenBinding;
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
 */
public class ChallengeScreen extends AppCompatActivity {

    private static final String CHALLENGE_ACTIVITY_USER_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION";

    private ActivityChallengeScreenBinding binding;
    private ProgressViewModel progressViewModel;
    private QuestionViewModel questionViewModel;
    private AnswerViewModel answerViewModel;

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;
    private RadioButton selectedAnswer;

    /**
     * Called when the activity is first created.
     * This method sets up the user interface, initializes the ViewModels, retrieves the challenge details and questions,
     * and sets up event listeners for user interactions.
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengeScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModels
        progressViewModel = new ViewModelProvider(this).get(ProgressViewModel.class);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        answerViewModel = new ViewModelProvider(this).get(AnswerViewModel.class);

        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION);

        setupUI();
        setupListeners();
        loadChallengeData();
    }

    /**
     * Sets up the user interface elements based on challenge details.
     */
    private void setupUI() {
        binding.challengeScreenHeader.setText(challengeName);
        binding.challengeScreenDescription.setText(challengeDescription);
    }

    /**
     * Sets up the event listeners for UI components.
     */
    private void setupListeners() {
        binding.backButtonChallengeScreen.setOnClickListener(v -> {
            Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
            startActivity(intent);
        });

        binding.submitButton.setOnClickListener(view -> {
            if (selectedAnswer != null) {
                checkAnswerAndUpdateProgress();
            }
        });

        binding.radioGroup.setOnCheckedChangeListener((radioGroup, checkedButtonID) -> {
            selectedAnswer = radioGroup.findViewById(checkedButtonID);
        });
    }

    /**
     * Loads the challenge data including questions and answers.
     */
    private void loadChallengeData() {
        questionViewModel.getQuestionsByChallengeId(challengeId).observe(this, questions -> {
            if (questions != null && !questions.isEmpty()) {
                displayQuestion(questions.get(0)); // Assuming single question display for simplicity
            }
        });
    }

    /**
     * Displays a question and its answers in the UI.
     *
     * @param question The question to display.
     */
    private void displayQuestion(Question question) {
        answerViewModel.getAnswersByQuestionId(question.getQuestionId()).observe(this, questionWithAnswers -> {
            binding.questionText.setText(question.getQuestionText());
            List<Answer> answers = questionWithAnswers.get(0).answers;  // Assume the first entry contains the answers
            binding.radioButton1.setText(answers.get(0).getAnswerText());
            binding.radioButton2.setText(answers.get(1).getAnswerText());
            binding.radioButton3.setText(answers.get(2).getAnswerText());
            binding.radioButton4.setText(answers.get(3).getAnswerText());
        });
    }

    /**
     * Checks the selected answer and updates the challenge progress accordingly.
     */
    private void checkAnswerAndUpdateProgress() {
        answerViewModel.getAnswersByQuestionId(challengeId).observe(this, questionWithAnswers -> {
            boolean isCorrect = selectedAnswer.getText().equals(questionWithAnswers.get(0).answers.get(0).getAnswerText()); // Assume the first answer is correct for simplicity
            if (isCorrect) {
                progressViewModel.getProgressByUserId(userId).observe(this, progressList -> {
                    for (Progress progress : progressList) {
                        if (progress.getChallengeId() == challengeId) {
                            progress.setLevel(progress.getLevel() + 1);
                            if (progress.getLevel() == questionViewModel.getQuestionsByChallengeId(challengeId).getValue().size()) {
                                progress.setStatus("isComplete");
                                progress.setCompletionDate(LocalDateTime.now());
                            }

                        }
                    }
                });
            } else {
                // Handle incorrect answer scenario
            }
        });
    }

    /**
     * Creates an intent for starting the {@link ChallengeScreen} activity.
     *
     * @param context             The context to use for creating the intent.
     * @param userId              The ID of the user.
     * @param challengeId         The ID of the challenge.
     * @param challengeName       The name of the challenge.
     * @param challengeDescription The description of the challenge.
     * @return The intent to start {@link ChallengeScreen}.
     */
    public static Intent challengeIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengeScreen.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}
