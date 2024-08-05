package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityAdminEditBinding;
import com.example.jmmdapplication.util.SessionManager;

import java.util.List;

public class AdminEditActivity extends AppCompatActivity {

    // get the binding
    private ActivityAdminEditBinding binding;
    private DatabaseRepository repository;
    private UserWithDetails userWithDetails;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityAdminEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dashboardButton.setOnClickListener(view -> finish());

        repository = DatabaseRepository.getRepository(getApplication());

        assert repository != null;

        // get all the users and their info.
        List<UserWithDetails> usersWithDetails = repository.getUsersWithDetails();

        setupRecyclerView(usersWithDetails);    }

    private void setupRecyclerView(List<UserWithDetails> users) {
        UserAdapter adapter = new UserAdapter(users);
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.userRecyclerView.setAdapter(adapter);
    }

}