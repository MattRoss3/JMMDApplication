package com.example.jmmdapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;
import com.example.jmmdapplication.util.SessionManager;

/**
 * The settings page activity of the application.
 * <p>
 * This activity provides options for users to configure their settings. It allows users to interact with settings-related UI elements.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 */

public class Settings_page extends AppCompatActivity {
    private ActivitySettingsPageBinding binding;
    private DatabaseRepository repository;
    private UserWithDetails userWithDetails;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        binding=ActivitySettingsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= MainUserInterface.intentFactory(getApplicationContext());
//                startActivity(intent);
            }
        });
        binding.changeusernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserName();
            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
        int userId = SessionManager.getUserSession(this);
        userWithDetails = repository.getUserWithDetails(userId);
        user=userWithDetails.user;


    }
    private void changePassword(){
        if(binding.PasswordSignInEditText.getText().toString().trim().equals(user.getPassword())){
            Toast.makeText(this, "Password is already set.", Toast.LENGTH_SHORT).show();
        } else if (binding.PasswordSignInEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password can not be changed", Toast.LENGTH_SHORT).show();
        } else{
            user.setPassword(binding.PasswordSignInEditText.getText().toString().trim());
            Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
        }
    }
    private void changeUserName(){
        if(binding.userNameSettingsEditText.getText().toString().trim().equals(user.getUsername())){
            Toast.makeText(this, "Username is already set.", Toast.LENGTH_SHORT).show();
        } else if (binding.userNameSettingsEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username can not be changed.", Toast.LENGTH_SHORT).show();
        } else{
            user.setUsername(binding.userNameSettingsEditText.getText().toString().trim());
            Toast.makeText(this, "Username successfully changed", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Creates an intent for starting the {@link Settings_page} activity.
     *
     * @param context The context to use for creating the intent.
     * @return An intent for starting the {@link Settings_page} activity.
     */
    static Intent intentFactory(Context context){
        return new Intent(context, Settings_page.class);

    }
}