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
import com.example.jmmdapplication.util.SessionManager;

/**
 * This application will act as a quick and easy language learning app.
 *
 * @authors Jerrick Wallace, Mohamed Othman, Matthew Ross, Dakota Fouch
 * @since 07/20/2024
 * CST 338 Software Design with Dr. C
 * wk05: Project 2
 */

public class MainUserInterface extends AppCompatActivity {


    private User user;
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

        // You have the User object, you can now use it to get the user's information and other data.
        user = repository.getUserById(SessionManager.getUserSession(this));

    }

    static Intent MainUserInterfaceIntentFactory(Context context){
        return new Intent(context, MainUserInterface.class);
    }



}