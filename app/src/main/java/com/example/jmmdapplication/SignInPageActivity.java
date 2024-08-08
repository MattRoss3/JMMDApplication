package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivitySignInPageBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserViewModel;

/**
 * Activity for handling user sign-in.
 * This activity provides a user interface for signing in, validates user credentials, and manages user sessions.
 */
public class SignInPageActivity extends AppCompatActivity {

    private ActivitySignInPageBinding binding;
    private UserViewModel userViewModel;

    /**
     * Called when the activity is first created.
     * This method sets up the user interface, initializes the ViewModel, and sets up listeners for the sign-in and sign-up buttons.
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
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

        userViewModel.getUserByUsernameAndPassword(username, password).observe(this, user -> {
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
