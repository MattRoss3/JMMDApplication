package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityChallengeScreenMultipleChoiceBinding;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Activity that displays a challenge and its associated questions to the user.
 * <p>
 * This activity handles the display of a challenge, its questions, and multiple-choice answers.
 * It also manages user interactions for answering questions and updating challenge progress.
 * </p>
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 * @authors Jerrick Wallace, Matthew Ross, Mohamed Othman, Dakota Fouch
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
    private DatabaseRepository repository;

    public static final String TAG = "CHALLENGE_SCREEN_MULTIPLE_CHOICE_TAG";

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;
    private Progress progress; // the user's progress for this challenge
    private RadioButton selectedAnswer; // the answer button selected by the user
    private int questionAttemptCounter = 0; // the counter for answer attempts by the user
    private int amountOfQuestionsInChallenge = 0; // the amount of questions in the challenge

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChallengeScreenMultipleChoiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get the user's id, the challenge's id, the challenge name, and challenge description
        // passed from the previous activity
        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION);

        repository = DatabaseRepository.getRepository(this.getApplication());

        binding.challengeScreenHeader.setText(challengeName); // set the challenge title header
        binding.challengeScreenDescription.setText(challengeDescription); // set the challenge description header

        progress = findProgress(); // create a local Progress object containing the user's progress for this challenge from the database

        ArrayList<Question> challengeQuestions = repository.getQuestionsByChallengeId(challengeId); // pull an ArrayList of Question objects under this Challenge by calling Repo method with challengeId
        amountOfQuestionsInChallenge = challengeQuestions.size();// create local variable of amount of questions in challenge

        displayQuestion(challengeQuestions, questionAttemptCounter); // display the first question in the challenge to the user
        identifySelectedAnswer(); // record their selected answer

        // set on click listener for submit button
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAnswer != null) { // ensure user has selected an answer
                    Question question = challengeQuestions.get(questionAttemptCounter); // get copy of current Question in challenge
                    Answer correctAnswer = findCorrectAnswer(repository.getAnswersByQuestionId(question.getQuestionId())); // get copy of the correct Answer in this challenge
                    if (correctAnswer == null) { // ensures the question in the database has a correct answer associated with it
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, there is a unique issue with displaying this question. Returning to main screen.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    }

                    if (selectedAnswer.getText().equals(correctAnswer.getAnswerText())) { // the user chose the correct answer

                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Congrats! That is the correct answer", Toast.LENGTH_SHORT).show(); // popup message notifying user they answered correctly

                        progress.setLevel(progress.getLevel() + 1); // increment the user's current level in this challenge

                        repository.updateProgress(progress); // update the user's progress in the database

                    } else { // the user chose the incorrect answer
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, the correct answer was " + correctAnswer.getAnswerText(), Toast.LENGTH_SHORT).show(); // popup message notifying user they answered incorrectly
                    }

                    ++questionAttemptCounter; // count the user's current attempt

                    if (progress.getLevel() == amountOfQuestionsInChallenge) { // the user finished the challenge and answered each question correctly
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Congratulations! You completed all questions in this challenge. Returning to Main Menu.", Toast.LENGTH_SHORT).show(); // popup message notifying user they completed the challenge

                        progress.setStatus("isComplete"); // set user progress for this challenge as complete
                        progress.setCompletionDate(LocalDateTime.now()); // record the date and time the user completed this challenge
                        repository.updateProgress(progress); // update the user's progress in the database

                        // Challenge finished, returning to Main User Interface
                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    } else if (questionAttemptCounter == amountOfQuestionsInChallenge) { // the user missed at least 1 question, but finished the challenge
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Challenge finished, but not 100%. Returning to main menu", Toast.LENGTH_SHORT).show(); // popup message notifying user they finished the challenge, but aren't 100%

                        // Challenge finished, returning to Main User Interface
                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    } else { // the challenge isn't finished
                        displayQuestion(challengeQuestions, questionAttemptCounter); // display the next question in the challenge for the user
                        identifySelectedAnswer(); // record their selected answer
                    }
                }
            }
        });

        // set setOnClickListener to back button to return to main user interface
        binding.backButtonChallengeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * Displays the current question in the challenge. Binds the possible answers to radio buttons for
     * the user to choose.
     *
     * @param challengeQuestions An ArrayList of Questions under this Challenge.
     * @param questionIndexInChallenge An int representing the index of the current question in this challenge.
     *
     */
    private void displayQuestion (ArrayList<Question> challengeQuestions, int questionIndexInChallenge) {
        binding.radioGroup.clearCheck(); // clear any checked buttons

        Question question = challengeQuestions.get(questionIndexInChallenge); // get current question in challenge
        ArrayList<Answer> questionAnswers = repository.getAnswersByQuestionId(question.getQuestionId()); // get list of possible answers for question
        Collections.shuffle(questionAnswers); // randomize the order of answers before displaying

        binding.questionText.setText(question.getQuestionText()); // set the question text to the current question
        binding.radioButton1.setText(questionAnswers.get(0).getAnswerText()); // set the first answer text to the current question
        binding.radioButton2.setText(questionAnswers.get(1).getAnswerText()); // set the second answer text to the current question
        binding.radioButton3.setText(questionAnswers.get(2).getAnswerText()); // set the third answer text to the current question
        binding.radioButton4.setText(questionAnswers.get(3).getAnswerText()); // set the fourth answer text to the current question
    }

    /**
     * Records the answer that the user chose.
     */
    private void identifySelectedAnswer() {
        selectedAnswer = null; // initialize the selectedAnswer as null
        binding.radioGroup.clearCheck(); // clear any checked buttons

        // set onCheckedChangeListener to button in radio group to identify user selected answer
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {
                selectedAnswer = (RadioButton) radioGroup.findViewById(checkedButtonID);
            }
        });
    }

    /**
     * Finds the correct Answer for the current question.
     *
     * @param questionAnswers An ArrayList of Answers under this Question.
     *
     * @return An Answer object that is the correct answer for the current Question.
     */
    private Answer findCorrectAnswer(ArrayList<Answer> questionAnswers) {

        for (Answer currAnswer : questionAnswers) { // iterate through each possible answer for this question
            if (currAnswer.isCorrect()) { // determine if answer has field indicating it's the correct answer
                return currAnswer;
            }
        }
        return null; // this question doesn't have a correct answer in the database
    }

    /**
     * Finds the Progress of this Challenge for the user.
     *
     * @return A Progress object that represents the user's progress for this challenge
     */
    private Progress findProgress() {
        List<Progress> allProgress = repository.getProgressByUserId(userId);  // pull a List of Progress objects under this Challenge by calling Repo method with userId
        if (allProgress.isEmpty()) { // this is the first challenge the user has started
            return new Progress(userId, challengeId, "inProgress", LocalDateTime.of(1970, 1, 1, 1, 1, 1), 0); // initialize the progress object for this challenge for the user
        } else {
            for (Progress currProgress : allProgress) { // iterate through the user's progress for each challenge until finding the progress for this challenge
                if (currProgress.getChallengeId() == challengeId) { // the user has worked on this challenge before
                    return currProgress; // create a copy of their progress for this challenge
                }
            }
        }
        // this challenge is new to the user
        return new Progress(userId, challengeId, "inProgress", LocalDateTime.of(1970, 1, 1, 1, 1, 1), 0); // initialize the progress object for this challenge for the user
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