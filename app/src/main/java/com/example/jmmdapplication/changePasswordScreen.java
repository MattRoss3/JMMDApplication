package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.databinding.ActivityAdvancedSettingsBinding;
import com.example.jmmdapplication.databinding.ActivityChangePasswordScreenBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;
import com.example.jmmdapplication.util.SessionManager;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import java.util.Objects;

public class changePasswordScreen extends AppCompatActivity {
    private ActivityChangePasswordScreenBinding binding;
    private UserViewModel userViewModel;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChangePasswordScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        int userId = SessionManager.getUserSession(this);

        userViewModel.getUserById(userId).observe(this, user -> {
            if (user != null) {
                this.user = user;
            }
        });
        binding.changepasswordButton.setOnClickListener(v -> changePassword());
        binding.backButtonSettingsScreen.setOnClickListener(v-> SettingsPage.intentFactory(getApplicationContext()));

        }
    private void changePassword() {
        String newPassword1 = Objects.requireNonNull(binding.passwordSignInEditText1.getText()).toString().trim();
        String newPassword2= Objects.requireNonNull(binding.passwordSignInEditText.getText()).toString().trim();
        String username= Objects.requireNonNull(binding.userNameChangepwInScreenEditText.getText()).toString().trim();
        String password= Objects.requireNonNull(binding.passwordSignInEditText.getText().toString().trim());
        if(user.getUsername().equals(username))
        {
            if(user.getPassword().equals(password)) {
                if (newPassword1.isEmpty() || newPassword2.isEmpty()) {
                    Toast.makeText(this, "New Password cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (newPassword1.equals(user.getPassword())) {
                    Toast.makeText(this, "Password is already set.", Toast.LENGTH_SHORT).show();
                } else if (!(newPassword1.equals(newPassword2))) {
                    Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                } else {
                    user.setPassword(newPassword1);
                    userViewModel.update(user);
                    Toast.makeText(this, "Password successfully changed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "Incorrect Current Password", Toast.LENGTH_SHORT).show();
            }
            }
            else{
                Toast.makeText(this, "You do not have access to this user", Toast.LENGTH_SHORT).show();
            }

    }
    public static Intent intentFactory(Context context) {
        return new Intent(context, changePasswordScreen.class);
    }
    }

