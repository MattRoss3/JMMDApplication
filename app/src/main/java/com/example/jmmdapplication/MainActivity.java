package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.jmmdapplication.MAIN_ACTIVITY_USER_ID";

    // create a binding object
    private ActivityMainBinding binding;
    private int loggedInUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        loginUser();

        if (loggedInUser == -1) {
            Intent intent = signInPageActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        DatabaseRepository repository = DatabaseRepository.getRepository(getApplication());


        User newUser = new User("username", "password", false);
        assert repository != null;
        repository.insertUser(newUser);


    }

    private void loginUser() {
        // check if user is logged in
        loggedInUser = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }
}