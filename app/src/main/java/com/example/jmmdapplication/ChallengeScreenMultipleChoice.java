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
import java.util.List;

/**
 * Activity that displays a challenge and its associated questions to the user.
 * <p>
 * This activity handles the display of a challenge, its questions, and multiple-choice answers.
 * It also manages user interactions for answering questions and updating challenge progress.
 * </p>
 *
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
    private Progress progress;
    private RadioButton selectedAnswer;
    //private Answer correctAnswer;
    private int questionAttemptCounter = 0;
    private int amountOfQuestionsInChallenge = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChallengeScreenMultipleChoiceBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION);

        repository = DatabaseRepository.getRepository( this.getApplication());

        progress = findProgress();

        binding.backButtonChallengeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.challengeScreenHeader.setText(challengeName);
        binding.challengeScreenDescription.setText(challengeDescription);

        ArrayList<Question> challengeQuestions = repository.getQuestionsByChallengeId(challengeId);
        amountOfQuestionsInChallenge = (challengeQuestions.size() - 1);
        questionAttemptCounter = 0;


        displayQuestion(challengeQuestions, questionAttemptCounter);

        identifySelectedAnswer();

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAnswer != null) {
                    Question question = challengeQuestions.get(questionAttemptCounter);
                    Answer correctAnswer = findCorrectAnswer(repository.getAnswersByQuestionId(question.getQuestionId()));
                    if (correctAnswer == null) {
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, there is a unique issue with displaying this question. Returning to main screen.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    }

                    if (selectedAnswer.getText().equals(correctAnswer.getAnswerText())) { // the user chose the correct answer
                        // update progress for this challenge
                        // record the date this challenge was completed
                        // increment level
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Congrats! That is the correct answer", Toast.LENGTH_SHORT).show();

                        progress.setLevel(progress.getLevel() + 1);

                        repository.updateProgress(progress);

                    } else { // the user chose the incorrect answer
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, the correct answer was " + correctAnswer.getAnswerText(), Toast.LENGTH_SHORT).show();
                    }

                    ++questionAttemptCounter;
                    if (progress.getLevel() == amountOfQuestionsInChallenge) {
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Congratulations! You completed all questions in this challenge. Returning to Main Menu.", Toast.LENGTH_SHORT).show();

                        progress.setStatus("isComplete");
                        progress.setCompletionDate(LocalDateTime.now());
                        repository.updateProgress(progress);

                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    } else if (questionAttemptCounter == amountOfQuestionsInChallenge) {
                        Toast.makeText(ChallengeScreenMultipleChoice.this, "Challenge finished, but not 100%. Returning to main menu", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
                        startActivity(intent);
                    } else {
                        displayQuestion(challengeQuestions, questionAttemptCounter);
                        identifySelectedAnswer();
                    }
                }

            }
        });


//        for (int i = 0; i < amountOfQuestionsInChallenge; i++) {
//            selectedAnswer = null;
//            binding.radioGroup.clearCheck();
//            iterateThroughQuestions(challengeQuestions);
//        }






//        for (Question question : challengeQuestions) {
//            ++questionAttemptCounter;
//            //Display question, answers in multiple choice format with radio buttons, & submit button for each question in the challenge
//            ArrayList<Answer> questionAnswers = repository.getAnswersByQuestionId(question.getQuestionId()); // get list of possible answers
//            Answer correctAnswer = null;
//
//            for (Answer currAnswer : questionAnswers) {
//                if (currAnswer.isCorrect()) {
//                    correctAnswer = currAnswer;
//                }
//            }
//            if (correctAnswer == null) {
//                Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, there is a unique issue with displaying this question. Returning to main screen.", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
//                startActivity(intent);
//            }
//
////            binding.challengeScreenHeader.setText(challengeName);
////            binding.challengeScreenDescription.setText(challengeDescription);
//
//            //Label the question and answers
//            //TODO:randomize
////            binding.questionText.setText(question.getQuestionText());
////            binding.radioButton1.setText(questionAnswers.get(0).getAnswerText());
////            binding.radioButton2.setText(questionAnswers.get(1).getAnswerText());
////            binding.radioButton3.setText(questionAnswers.get(2).getAnswerText());
////            binding.radioButton4.setText(questionAnswers.get(3).getAnswerText());
//
//            //TODO: Create submit button & determine if selected answer is correct, update database accordingly
////            binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////                @Override
////                public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {
////                    selectedAnswer = (RadioButton) radioGroup.findViewById(checkedButtonID);
////                }
////            });
//
////            Answer finalCorrectAnswer = correctAnswer;
////            binding.submitButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////                    if (selectedAnswer.getText().equals(finalCorrectAnswer.getAnswerText())) { // the user chose the correct answer
////                        // update progress for this challenge
////                        // record the date this challenge was completed
////                        // increment level
//////                        Toast.makeText(ChallengeScreenMultipleChoice.this, "TEST2", Toast.LENGTH_SHORT).show();
////
//////                        List<Progress> allProgress = repository.getProgressByUserId(userId);
//////                        for (Progress currProgress : allProgress) {
//////                            Toast.makeText(ChallengeScreenMultipleChoice.this, "TEST3", Toast.LENGTH_SHORT).show();
//////
//////                            if (currProgress.getChallengeId() == challengeId) {
//////                                Progress progress = currProgress;
//////                                progress.setLevel(progress.getLevel() + 1);
//////
//////                                repository.updateProgress(progress);
//////
//////                                if (progress.getLevel() == (challengeQuestions.size() - 1)) {
//////                                    Toast.makeText(ChallengeScreenMultipleChoice.this, "Congratulations! You completed all questions in this challenge.", Toast.LENGTH_SHORT).show();
//////
//////                                    progress.setStatus("isComplete");
//////                                    progress.setCompletionDate(LocalDateTime.now());
//////                                    repository.updateProgress(progress);
//////                                }
//////                            }
//////                        }
////                        progress.setLevel(progress.getLevel() + 1);
////
////                        repository.updateProgress(progress);
////
////                        if (progress.getLevel() == amountOfQuestionsInChallenge) {
////                            Toast.makeText(ChallengeScreenMultipleChoice.this, "Congratulations! You completed all questions in this challenge. Returning to Main Menu.", Toast.LENGTH_SHORT).show();
////
////                            progress.setStatus("isComplete");
////                            progress.setCompletionDate(LocalDateTime.now());
////                            repository.updateProgress(progress);
////
////                            Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
////                            startActivity(intent);
////                        }
////
////
////                    } else { // the user chose the incorrect answer
////
////                        if (questionAttemptCounter == amountOfQuestionsInChallenge) {
////                            Toast.makeText(ChallengeScreenMultipleChoice.this, "Challenge finished, but not 100%. Returning to main menu", Toast.LENGTH_SHORT).show();
////
////                            Intent intent = new Intent(MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext()));
////                            startActivity(intent);
////                        } else {
////                            Toast.makeText(ChallengeScreenMultipleChoice.this, "Sorry, the correct answer was " + questionAnswers.get(0).getAnswerText(), Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                }
////            });
//        }
    }

    private void iterateThroughQuestions(ArrayList<Question> challengeQuestions) {


    }

    private void displayQuestion (ArrayList<Question> challengeQuestions, int questionIndexInChallenge) {
        selectedAnswer = null;
        binding.radioGroup.clearCheck();

        Question question = challengeQuestions.get(questionIndexInChallenge); // get current question in challenge
        ArrayList<Answer> questionAnswers = repository.getAnswersByQuestionId(question.getQuestionId()); // get list of possible answers for question

//        binding.challengeScreenHeader.setText(challengeName);
//        binding.challengeScreenDescription.setText(challengeDescription);

        binding.questionText.setText(question.getQuestionText());
        binding.radioButton1.setText(questionAnswers.get(0).getAnswerText());
        binding.radioButton2.setText(questionAnswers.get(1).getAnswerText());
        binding.radioButton3.setText(questionAnswers.get(2).getAnswerText());
        binding.radioButton4.setText(questionAnswers.get(3).getAnswerText());

//        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {
//                selectedAnswer = (RadioButton) radioGroup.findViewById(checkedButtonID);
//            }
//        });
    }

    private void identifySelectedAnswer() {
        selectedAnswer = null;
        binding.radioGroup.clearCheck();

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {
                selectedAnswer = (RadioButton) radioGroup.findViewById(checkedButtonID);
            }
        });
    }

    private Answer findCorrectAnswer(ArrayList<Answer> questionAnswers) {

        for (Answer currAnswer : questionAnswers) {
            if (currAnswer.isCorrect()) {
                return currAnswer;
            }
        }
        return null;
    }

    private Progress findProgress() {
        List<Progress> allProgress = repository.getProgressByUserId(userId);
        if (allProgress.isEmpty()) { // This is the first challenge the user has started
            return new Progress(userId, challengeId, "inProgress", LocalDateTime.of(1970, 1, 1, 1, 1, 1), 0); // initialize the progress object for this challenge for the user
        } else {
            for (Progress currProgress : allProgress) {
                if (currProgress.getChallengeId() == challengeId) { // The user has worked on this challenge before
                    return currProgress; // create a copy of their progress for this challenge
                }
            }
        }
        // This challenge is new to the user
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