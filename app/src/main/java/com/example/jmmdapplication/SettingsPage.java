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



        binding.backButtonSettingsScreen.setOnClickListener(v -> navigateToMainUserInterface());
        binding.changeusernameButton.setOnClickListener(v -> navigatetoAdvancedSettings());
        binding.changePasswordButton.setOnClickListener(v -> navigateToChangePassword());

    }



    /**
     * Navigates to the main user interface.
     */
    private void navigateToMainUserInterface() {
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
        startActivity(intent);
    }
    private void navigatetoAdvancedSettings(){
        Intent intent = AdvancedSettings.intentFactory(getApplicationContext());
        startActivity(intent);
    }
    private void navigateToChangePassword(){
        Intent intent=changePasswordScreen.intentFactory(getApplicationContext());
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
