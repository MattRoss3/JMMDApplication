package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserViewModel;

/**
 * The settings page activity of the application.
 * <p>
 * This activity provides options for users to configure their settings. It allows users to interact with settings-related UI elements.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">JMMDApplication</a>
 */
public class SettingsPage extends AppCompatActivity {
    private ActivitySettingsPageBinding binding;
    private UserViewModel userViewModel;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        int userId = SessionManager.getUserSession(this);

        userViewModel.getUserById(userId).observe(this, user -> {
            if (user != null) {
                this.user = user;
                setupUI();
            }
        });

        binding.backButtonSettingsScreen.setOnClickListener(v -> navigateToMainUserInterface());
        binding.changeusernameButton.setOnClickListener(v -> changeUsername());
        binding.button.setOnClickListener(v -> changePassword());
    }

    /**
     * Sets up the user interface with the current user details.
     */
    private void setupUI() {
        binding.userNameSettingsEditText.setText(user.getUsername());
        binding.PasswordSignInEditText.setText(user.getPassword());
    }

    /**
     * Changes the username of the user.
     */
    private void changeUsername() {
        String newUsername = binding.userNameSettingsEditText.getText().toString().trim();
        if (newUsername.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if (newUsername.equals(user.getUsername())) {
            Toast.makeText(this, "Username is already set.", Toast.LENGTH_SHORT).show();
        } else {
            user.setUsername(newUsername);
            userViewModel.update(user);
            Toast.makeText(this, "Username successfully changed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Changes the password of the user.
     */
    private void changePassword() {
        String newPassword = binding.PasswordSignInEditText.getText().toString().trim();
        if (newPassword.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if (newPassword.equals(user.getPassword())) {
            Toast.makeText(this, "Password is already set.", Toast.LENGTH_SHORT).show();
        } else {
            user.setPassword(newPassword);
            userViewModel.update(user);
            Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Navigates to the main user interface.
     */
    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    /**
     * Creates an intent for starting the {@link SettingsPage} activity.
     *
     * @param context The context to use for creating the intent.
     * @return An intent for starting the {@link SettingsPage} activity.
     */
    public static Intent intentFactory(Context context) {
        return new Intent(context, SettingsPage.class);
    }
}
