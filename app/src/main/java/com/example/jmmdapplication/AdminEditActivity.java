package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityAdminEditBinding;
import com.example.jmmdapplication.util.SessionManager;

public class AdminEditActivity extends AppCompatActivity {

    // get the binding
    private ActivityAdminEditBinding binding;
    private DatabaseRepository repository;
    private UserWithDetails userWithDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityAdminEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dashboardButton.setOnClickListener(view -> finish());

        repository = DatabaseRepository.getRepository(getApplication());

        // get the user id from the session manager
        int userId = SessionManager.getUserSession(this);

        userWithDetails = repository.getUserWithDetails(userId);



    }
}