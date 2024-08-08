package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.entities.UserChallenge;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityAdminEditBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.util.SwipeToDeleteCallback;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for editing user details from an admin perspective.
 * <p>
 * This activity displays a list of users with their details and allows for user deletion through swipe gestures.
 * </p>
 */

public class AdminEditActivity extends AppCompatActivity {

    // get the binding
    private ActivityAdminEditBinding binding;
    private DatabaseRepository repository;
    private UserAdapter userAdapter;
    private UserViewModel userViewModel;
    private UserChallengeViewModel userChallengeViewModel;


    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the user interface, initializes the repository, retrieves the list of users with details,
     * and sets up the RecyclerView with a swipe-to-delete feature.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}. <b>Note:</b> Otherwise it is null.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityAdminEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dashboardButton.setOnClickListener(view -> finish());

        repository = DatabaseRepository.getRepository(this.getApplication());

        assert repository != null;

        int userId = SessionManager.getUserSession(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userChallengeViewModel = new ViewModelProvider(this).get(UserChallengeViewModel.class);


        userViewModel.getAllUsers().observe(this, users -> {
            userChallengeViewModel.getChallengesAssignedToUser(userId).observe(this, usersWithChallenges -> {
                setupRecyclerView(users, usersWithChallenges);
            });
        });

    }

    /**
     * Configures the RecyclerView for displaying the list of users.
     * <p>
     * Sets up the RecyclerView's layout manager, adapter, and attaches a swipe-to-delete callback to enable item removal.
     * </p>
     *
     * @param users A list of {@link User & Challenges} to be displayed in the RecyclerView.
     */

    private void setupRecyclerView(List<User> users, UsersWithChallenges usersWithChallenges) {
        UserAdapter adapter = new UserAdapter(users,usersWithChallenges, userViewModel);
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.userRecyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.userRecyclerView);
    }

}