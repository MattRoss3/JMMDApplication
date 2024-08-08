package com.example.jmmdapplication;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivitySignUpPageBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserViewModel;

/**
 * Activity for handling user sign-up.
 * This activity provides a user interface for signing up, validates user input, and creates a new user in the database.
 */
public class SignUpPageActivity extends AppCompatActivity {
    private ActivitySignUpPageBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize UserViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        setupListeners();
    }

    /**
     * This method will pull the username and password from the sign-up screen and create a new user in the database.
     * If the username is already taken, it will show an error message.
     * If the password fields do not match, it will show an error message.
     * If the user is created successfully, it will save the user session and redirect to the sign-in screen.
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

        userViewModel.getUserByUsername(username).observe(this, existingUser -> {
            if (existingUser == null) {
                // Create new user
                User newUser = new User(username, password, false);
                userViewModel.insert(newUser);
                SessionManager.saveUserSession(SignUpPageActivity.this, newUser.getUserId());

                Toast.makeText(SignUpPageActivity.this, "Account created successfully. Please log in.", Toast.LENGTH_SHORT).show();
                finish(); // Close the sign-up activity and return to the sign-in screen
            } else {
                Toast.makeText(SignUpPageActivity.this, "Username already taken. Please choose another.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets up the event listeners for the UI components.
     */
    private void setupListeners() {
        binding.signUpButton.setOnClickListener(view -> signUp());
        binding.cancelButton.setOnClickListener(view -> finish());
    }
}
