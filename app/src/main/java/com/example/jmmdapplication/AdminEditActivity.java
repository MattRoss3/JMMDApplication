package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivityAdminEditBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.util.SwipeToDeleteCallback;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.List;

/**
 * Activity for editing user details from an admin perspective.
 * <p>
 * This activity displays a list of users with their details and allows for user deletion through swipe gestures.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 */
public class AdminEditActivity extends AppCompatActivity {

    private ActivityAdminEditBinding binding;
    private UserViewModel userViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private UserAdapter userAdapter;

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the user interface, initializes the ViewModels, retrieves the list of users with details,
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

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userChallengeViewModel = new ViewModelProvider(this).get(UserChallengeViewModel.class);

        userViewModel.getAllUsers().observe(this, this::setupRecyclerView);
    }

    /**
     * Configures the RecyclerView for displaying the list of users.
     * <p>
     * Sets up the RecyclerView's layout manager, adapter, and attaches a swipe-to-delete callback to enable item removal.
     * </p>
     *
     * @param users A list of {@link User} to be displayed in the RecyclerView.
     */
    private void setupRecyclerView(List<User> users) {
        userAdapter = new UserAdapter(users, userViewModel);
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.userRecyclerView.setAdapter(userAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(userAdapter));
        itemTouchHelper.attachToRecyclerView(binding.userRecyclerView);
    }
}
