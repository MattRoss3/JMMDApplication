package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
 * This activity is the challenge section of the app. The user will be presented with a series of
 * language questions in the challenge they chose, using a write-in answer box. The user's progress
 * is kept track of. Once the user completes each question in the challenge, it is marked as
 * completed and the date is recorded.
 *
 *
 * @authors Matthew Ross, Mohamed Othman, Dakota Fouch
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
    private RadioButton selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChallengeScreenMultipleChoiceBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        userId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_USER_ID, -1);
        challengeId = getIntent().getIntExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, -1);
        challengeName = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME);
        challengeDescription = getIntent().getStringExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION);

        //challengeScreenViewModel = new ViewModelProvider(this).get([app]ViewModel.class); // TODO: ViewModel not yet exists

        //TODO: RecyclerView / viewHolders
//        RecyclerView recyclerView = binding.logDisplayRecyclerView;
//        final GymLogAdapter adapter = new GymLogAdapter(new GymLogAdapter.GymLogDiff());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = DatabaseRepository.getRepository(getApplication());

        binding.backButtonChallengeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //TODO: Display challenge title and description
        //1: find the challenge object

        //2: set the text for the header with the title and description
        //binding.challengeScreenHeader.setText();

        // TODO: Display the multiple choice question
        // TODO: ViewModel not yet exists
//        ViewModel.getQuestionsByChallengeId(challengeId).observe(this, questions -> { // TODO: pass a list of 4 answers to the adapter to display the 4 questions of the challenge
//            adapter.submitList(questions);
//        }
////
////            adapter.submitList(userChallenges);
////        });

        ArrayList<Question> challengeQuestions = repository.getQuestionsByChallengeId(challengeId);
        for (Question question : challengeQuestions) {
            //Display question, answers in multiple choice format with radio buttons, & submit button for each question in the challenge
            ArrayList<Answer> questionAnswers = repository.getAnswersByQuestionId(question.getQuestionId()); // get list of possible answers
            Answer correctAnswer = questionAnswers.get(0);

            binding.challengeScreenHeader.setText(challengeName);
            binding.challengeScreenDescription.setText(challengeDescription);

            //Label the question and answers
            //TODO:randomize
            binding.questionText.setText(question.getQuestionText());
            binding.radioButton1.setText(questionAnswers.get(0).getAnswerText());
            binding.radioButton2.setText(questionAnswers.get(1).getAnswerText());
            binding.radioButton3.setText(questionAnswers.get(2).getAnswerText());
            binding.radioButton4.setText(questionAnswers.get(3).getAnswerText());

            //TODO: Create submit button & determine if selected answer is correct, update database accordingly
            binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int checkedButtonID) {
                    selectedAnswer = (RadioButton) radioGroup.findViewById(checkedButtonID);
                }
            });

            binding.submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selectedAnswer.getText().equals(questionAnswers.get(0).getAnswerText())) { // the user chose the correct answer
                        // update progress for this challenge
                        // record the date this challenge was completed
                        // increment level

                        List<Progress> allProgress = repository.getProgressByUserId(userId);
                        for (Progress currProgress : allProgress) {
                            if (currProgress.getChallengeId() == challengeId) {
                                Progress progress = currProgress;
                                progress.setLevel(progress.getLevel() + 1);

                                if (progress.getLevel() == challengeQuestions.size()) {
                                    progress.setStatus("isComplete");
                                    progress.setCompletionDate(LocalDateTime.now());
                                }

                                repository.updateProgress(progress);
                            }
                        }

                    } else { // the user chose the incorrect answer

                    }
                }
            });
        }



        //binding.radioButton1.setText(getString(repository.));


    }

    public static Intent ChallengeMultipleChoiceIntentFactory(Context context, int userId, int challengeId, String challengeName, String challengeDescription) {
        Intent intent = new Intent(context, ChallengeScreenMultipleChoice.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, challengeId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_NAME, challengeName);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION, challengeDescription);
        return intent;
    }
}