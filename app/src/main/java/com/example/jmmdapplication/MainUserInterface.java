package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;
import com.example.jmmdapplication.viewmodel.UserViewModel;

/**
 * The main user interface activity of the application.
 * <p>
 * This activity displays the user's information, challenges, and provides navigation to different parts of the app.
 * It handles user session management, challenge display, and provides buttons for creating new challenges, logging out, and editing user details.
 * </p>
 */
public class MainUserInterface extends AppCompatActivity {
    private static final String CHALLENGE_PROMPT_USER_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_USER_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_ID = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_ID";
    private static final String CHALLENGE_PROMPT_CHALLENGE_NAME = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_NAME";
    private static final String CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION = "com.example.jmmdapplication.CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION";

    private ActivityMainUserInterfaceBinding binding;
    private UserViewModel userViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private ChallengeAdapter challengeAdapter;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModels
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userChallengeViewModel = new ViewModelProvider(this).get(UserChallengeViewModel.class);

        userId = SessionManager.getUserSession(this);

        setupUI();
        setupListeners();
        setupRecyclerView();
    }

    /**
     * Sets up the RecyclerView to display the list of challenges.
     */
    private void setupRecyclerView() {
        recyclerView = binding.myChallengesDisplayRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userChallengeViewModel.getChallengesAssignedToUser(userId).observe(this, usersWithChallenges -> {
            if (usersWithChallenges != null && usersWithChallenges.challenges != null) {
                challengeAdapter = new ChallengeAdapter(usersWithChallenges, challenge -> {
                    Intent intent = new Intent(MainUserInterface.this, ChallengePromptActivity.class);
                    intent.putExtra(CHALLENGE_PROMPT_USER_ID, userId);
                    intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_ID, challenge.getChallengeId());
                    intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_NAME, challenge.getName());
                    intent.putExtra(CHALLENGE_PROMPT_CHALLENGE_DESCRIPTION, challenge.getDescription());
                    startActivity(intent);
                });
                recyclerView.setAdapter(challengeAdapter);
            }
        });
    }

    /**
     * Sets up the user interface elements based on user details.
     */
    private void setupUI() {
        userViewModel.getUserById(userId).observe(this, user -> {
            if (user != null) {
                String welcomeMessage = getString(R.string.welcome_message, user.getUsername());
                binding.mainUserHeader.setText(welcomeMessage);

                if (user.isAdmin()) {
                    binding.editUserButton.setVisibility(View.VISIBLE);
                } else {
                    binding.editUserButton.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Sets up the event listeners for UI components.
     */
    private void setupListeners() {
        binding.newChallengeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainUserInterface.this, AddNewChallenge.class);
            startActivity(intent);
        });

        binding.logoutButton.setOnClickListener(v -> {
            SessionManager.clearUserSession(MainUserInterface.this);
            Intent intent = new Intent(MainUserInterface.this, SignInPageActivity.class);
            startActivity(intent);
            finish();
        });

        binding.editUserButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainUserInterface.this, AdminEditActivity.class);
            startActivity(intent);
        });

        binding.settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainUserInterface.this, SettingsPage.class);
            startActivity(intent);
        });
    }

    /**
     * Creates an intent for starting the {@link MainUserInterface} activity.
     *
     * @param context The context to use for creating the intent.
     * @return An intent for starting the {@link MainUserInterface} activity.
     */
    public static Intent MainUserInterfaceIntentFactory(Context context) {
        return new Intent(context, MainUserInterface.class);
    }
}
