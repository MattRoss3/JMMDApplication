package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainBinding;

/**
 * The main activity for the application that handles user login and session management.
 * <p>
 * This activity checks if a user is logged in by retrieving the user ID from shared preferences.
 * If a user ID is found, it welcomes the user; otherwise, it redirects to the sign-in page.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 */

public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY_USER_ID = "com.example.jmmdapplication.MAIN_ACTIVITY_USER_ID";
    private static final String USER_SESSION_PREFS = "UserSessionPrefs";
    private static final String USER_ID_KEY = "UserIdKey";

    // create a binding object
    private ActivityMainBinding binding;
    private int loggedInUser = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser();

        if (loggedInUser == -1) {
            // No user is logged in, redirect to the sign-in page
            Intent intent = new Intent(MainActivity.this, SignInPageActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is logged in, you can proceed with the normal flow of MainActivity
            Toast.makeText(this, "Welcome back, User ID: " + loggedInUser, Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Retrieves the logged-in user's ID from shared preferences.
     */

    private void loginUser() {
        SharedPreferences prefs = getSharedPreferences(USER_SESSION_PREFS, MODE_PRIVATE);
        loggedInUser = prefs.getInt(USER_ID_KEY, -1);
    }

    /**
     * Saves the user's session ID to shared preferences.
     *
     * @param context The context to use for accessing shared preferences.
     * @param userId  The ID of the user to save in the session.
     */

    public static void saveUserSession(Context context, int userId) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    /**
     * Clears the user's session from shared preferences.
     *
     * @param context The context to use for accessing shared preferences.
     */

    public static void clearUserSession(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(USER_SESSION_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_ID_KEY);
        editor.apply();
    }
}
