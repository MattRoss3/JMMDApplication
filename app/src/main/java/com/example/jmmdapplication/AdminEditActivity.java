package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityAdminEditBinding;
import com.example.jmmdapplication.util.SwipeToDeleteCallback;

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
    private UserWithDetails userWithDetails;
    private UserAdapter userAdapter;

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the user interface, initializes the repository, retrieves the list of users with details,
     * and sets up the RecyclerView with a swipe-to-delete feature.
     * </p>
     * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
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

        // get all the users and their info.
        List<UserWithDetails> usersWithDetails = repository.getUsersWithDetails();

        setupRecyclerView(usersWithDetails);    }

    /**
     * Configures the RecyclerView for displaying the list of users.
     * <p>
     * Sets up the RecyclerView's layout manager, adapter, and attaches a swipe-to-delete callback to enable item removal.
     * </p>
     *
     * @param users A list of {@link UserWithDetails} to be displayed in the RecyclerView.
     */

    private void setupRecyclerView(List<UserWithDetails> users) {
        UserAdapter adapter = new UserAdapter(users, repository);
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.userRecyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(binding.userRecyclerView);
    }

}