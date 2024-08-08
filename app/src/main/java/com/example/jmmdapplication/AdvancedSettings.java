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
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivityAdvancedSettingsBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.Objects;


public class AdvancedSettings extends AppCompatActivity {
    private ActivityAdvancedSettingsBinding binding;
    private UserViewModel userViewModel;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdvancedSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        int userId = SessionManager.getUserSession(this);

        userViewModel.getUserById(userId).observe(this, user -> {
            if (user != null) {
                this.user = user;
            }
        });
        binding.changeusernameButton.setOnClickListener(v -> changeUsername());
        binding.backButtonSettingsScreen.setOnClickListener(v-> SettingsPage.intentFactory(getApplicationContext()));
        };
    private void changeUsername() {
        String newUsername = Objects.requireNonNull(binding.newusernameSignInEditText.getText()).toString().trim();
        String username= Objects.requireNonNull(binding.userNameChangeunInScreenEditText.getText()).toString().trim();
        String password= Objects.requireNonNull(binding.passwordSignInEditText.getText().toString().trim());
        userViewModel.getUserByUsernameAndPassword(username,password).observe(this,user1 -> {
            if(user1.equals(user)){
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
            else{
                Toast.makeText(this, "You do not have access to this user", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public static Intent intentFactory(Context context) {
        return new Intent(context, SettingsPage.class);
    }

}