package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityChallengeScreenBinding;

import java.util.ArrayList;
import java.util.List;


public class ChallengeScreen extends AppCompatActivity {

    private static final String CHALLENGE_ACTIVITY_USER_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME";
    private static final String CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION";


    private ActivityChallengeScreenBinding binding;
    private DatabaseRepository repository;



    public static final String TAG = "CHALLENGE_SCREEN_TAG";

    private int userId;
    private int challengeId;
    private String challengeName;
    private String challengeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChallengeScreenBinding.inflate(getLayoutInflater());

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

            binding.challengeScreenHeader.setText(challengeName);
            binding.challengeScreenDescription.setText(challengeDescription);

            //Label the question and answers
            binding.questionText.setText(question.getQuestionText());
            binding.radioButton1.setText(questionAnswers.get(0).getAnswerText());
            binding.radioButton2.setText(questionAnswers.get(1).getAnswerText());
            binding.radioButton3.setText(questionAnswers.get(2).getAnswerText());
            binding.radioButton4.setText(questionAnswers.get(3).getAnswerText());

            //TODO: Create submit button & determine if selected answer is correct, update datatbase accordingly
        }

        binding.backButtonChallengeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        //binding.radioButton1.setText(getString(repository.));


    }

    public static Intent challengeIntentFactory(Context context, int userId, int challengeId) {
        Intent intent = new Intent(context, ChallengeScreen.class);
        intent.putExtra(CHALLENGE_ACTIVITY_USER_ID, userId);
        intent.putExtra(CHALLENGE_ACTIVITY_CHALLENGE_ID, challengeId);
        return intent;
    }



}