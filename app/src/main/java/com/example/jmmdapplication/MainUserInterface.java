package com.example.jmmdapplication;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

/**
 * This application will act as a quick and easy language learning app.
 *
 * @authors Jerrick Wallace, Mohamed Othman, Matthew Ross, Dakota Fouch
 * @since 07/20/2024
 * CST 338 Software Design with Dr. C
 * wk05: Project 2
 */

public class MainUserInterface extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.jmmdapplication.MAIN_ACTIVITY_USER_ID";
    static  final  String SHARED_PREFERENCE_USERID_KEY = "com.example.jmmdapplication.SHARED_PREFERENCE_USERID_KEY";
    static  final  String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.jmmdapplication.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainUserInterfaceBinding binding;
    private DatabaseRepository repository;

//    private [appViewModel]ViewModel gymLogViewModel; //TODO: app's ViewModel not yet exists

    public static final String TAG = "MAIN_USER_INTERFACE_TAG";

    private int loggedInUserID = -1;
    private User user; //TODO: User class not yet exists

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_interface);
        binding=ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        [appViewModel]ViewModel = new ViewModelProvider(this).get(GymLogViewModel.class); //TODO: app's ViewModel not yet exists

        //TODO: RecyclerView / viewHolders
//        RecyclerView recyclerView = binding.logDisplayRecyclerView;
//        final GymLogAdapterOR[AppAdapter] adapter = new AppAdapter(new AppAdapter.GymLogDiffOR[AppDiff]());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        repository = [appRepo].getRepository(getApplication()); //TODO: Repository not yet exists
//        loginUser(savedInstanceState); //TODO: implement loginUser() method

        //invalidateOptionsMenu();//TODO: NOT SURE IF NEEDED

        //TODO: ViewModel not yet exists
//        [appViewModel]ViewModel.getAllLogsById(loggedInUserID).observe(this, completedchallenges -> {
//            adapter.submitList(gymlogs);
//        });

        // a User isn't signed into the application, go to sign in screen
//        if (loggedInUserID == -1) {
//            Intent intent = signInPageActivity.loginIntentFactory(getApplicationContext()); //TODO: implement loginIntentFactory method
//            startActivity(intent);
//        }
//        updateSharedPreference(); //TODO: Implement updateSharedPreference()


        //TODO: implement progressBar for Overall Progress
//        int userProgress = repository.getProgressByUserId(loggedInUserID); //TODO: repo not yet exists
//        binding.progressBarMainInterface.setProgress(userProgress);


        //TODO: implement progress percentage & fractions for Language Progress
        // 1: get the user's completed challeneges per language
        // 2: get the total amount of added challenges per language
        // 3. display challenge stats per language

//        //TODO: This method might need to be implemented in onBindViewHolder() in adapter class
//        binding.myChallengesDisplayRecyclerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = ChallengeScreen.ChallengeScreenIntentFactory(getApplicationContext()); //TODO: Implement ChallengeScreenIntentFactory()
//                startActivity(intent);
//            }
//        });

//        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= signInPageActivity.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });

//        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = Settings_page.intentFactory(getApplicationContext());
//                Intent intent=Settings_page.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });

        binding.newChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= AddNewChallenge.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        //TODO: Might not be necessary if we can apply one OnClickListener to the RecyclerView
//        binding.button1MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button2MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button3MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button4MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button5MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button6MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button7MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button8MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button9MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//        binding.button10MainPage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent= ChallengeScreen.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });

        //CODE IDEAS
        // code from GymLog to refresh log data on screen.
        //TODO: can be adjusted to refresh challenges if needed
//        binding.logButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(MainActivity.this, "IT WORKED!", Toast.LENGTH_SHORT).show();
//                getInformationFromDisplay();
//                insertGymLogRecord();
//                //TODO: REMOVE LINE BELOW
////                updateDisplay();
//            }
//        });

        //TODO: Implement logout menu
        //        binding.logoutButton.setOnClickListener(new View.OnClickListener() { // UNSURE IF NEEDED
//            @Override
//            public void onClick(View v) {
//                Intent intent= signInPageActivity.intentFactory(getApplicationContext());
//                startActivity(intent);
//            }
//        });


        //TODO: to scroll
//        binding.logDisplayTextViewOR[layoutXML_TEXTVIEW].setMovementMethod(new ScrollingMovementMethod());

    }

    static Intent MainUserInterfaceIntentFactory(Context context){
        return new Intent(context, MainUserInterface.class);
    }

}