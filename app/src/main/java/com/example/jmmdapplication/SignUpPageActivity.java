package com.example.jmmdapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivitySignUpPageBinding;
import com.example.jmmdapplication.util.SessionManager;

public class SignUpPageActivity extends AppCompatActivity {
    private ActivitySignUpPageBinding binding;
    private DatabaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = DatabaseRepository.getRepository(getApplication());

        setupListeners();    }

    /**
     * This method will pull the username and password from the sign-up screen and create a new user in the database.
     * If the username is already taken, it will show an error message.
     * If the password fields do not match, it will show an error message.
     * If the user is created successfully, it will save the user session and redirect to the sign-in screen.
     * then will prompt the user to log in.
     */
    private void signUp() {
        String username = binding.usernameSignUp.getText() != null ? binding.usernameSignUp.getText().toString().trim().toLowerCase() : "";
        String password = binding.password1SignUp.getText() != null ? binding.password1SignUp.getText().toString().trim() : "";
        String confirmPassword = binding.password2SignUp.getText() != null ? binding.password2SignUp.getText().toString().trim() : "";

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User existingUser = repository.getUserByUsername(username);
            if (existingUser == null) {
                // Create new user
                User newUser = new User(username, password, false);
                repository.insertUser(newUser);
                SessionManager.saveUserSession(SignUpPageActivity.this, newUser.getUserId());

                runOnUiThread(() -> {
                    Toast.makeText(SignUpPageActivity.this, "Account created successfully. Please log in.", Toast.LENGTH_SHORT).show();
                    finish(); // Close the sign-up activity and return to the sign-in screen
                });
            } else {
                runOnUiThread(() -> {
                    Toast.makeText(SignUpPageActivity.this, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    public void setupListeners() {
        binding.signUpButton.setOnClickListener(view -> signUp());
        binding.cancelButton.setOnClickListener(view -> finish());
    }
}
