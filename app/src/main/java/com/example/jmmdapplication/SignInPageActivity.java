package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivitySignInPageBinding;
import com.example.jmmdapplication.util.SessionManager;

public class SignInPageActivity extends AppCompatActivity {
    ActivitySignInPageBinding binding;
    DatabaseRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = DatabaseRepository.getRepository( this.getApplication());
        setupListeners();

    }

    /**
     * This method will pull the username and password from the sign-in screen and check if the user exists in the database.
     * If the user exists, it will save the user session and redirect to the main user interface.
     * If the user does not exist, it will show an error message.
     */
    private void signInUser() {
        String username = binding.userNameSignInScreenEditText.getText() != null ? binding.userNameSignInScreenEditText.getText().toString().trim().toLowerCase() : "";
        String password = binding.passwordSignInEditText.getText() != null ? binding.passwordSignInEditText.getText().toString().trim() : "";

        AppDatabase.databaseWriteExecutor.execute(() -> {
            User user = repository.getUserByUsernameAndPassword(username, password);

            runOnUiThread(() -> {
                if (user != null) {
                    SessionManager.saveUserSession(SignInPageActivity.this, user.getUserId());
                    Intent intent = new Intent(SignInPageActivity.this, MainUserInterface.class);
                    startActivity(intent);
                    finish(); // Close the sign-in activity
                } else {
                    // Login failed, show error message
                    Toast.makeText(SignInPageActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    /**
     * Sets up listeners for the sign-in and sign-up buttons.
     * <p>
     * This method binds click listeners to the sign-in and sign-up buttons. The sign-in button triggers the {@link #signInUser()} method,
     * while the sign-up button navigates to the sign-up page.
     * </p>
     */

    private void setupListeners() {
        binding.signInButtonSignInPage.setOnClickListener(view -> signInUser());
        binding.signUpButtonSignInPage.setOnClickListener(view -> {
            Intent intent = new Intent(SignInPageActivity.this, SignUpPageActivity.class);
            startActivity(intent);
        });
    }
}
