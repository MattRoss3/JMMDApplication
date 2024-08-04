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

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;
import com.example.jmmdapplication.util.SessionManager;

public class Settings_page extends AppCompatActivity {
    private ActivitySettingsPageBinding binding;
    User user;

    private DatabaseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        binding=ActivitySettingsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButtonSettingsScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
               startActivity(intent);
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

        user = repository.getUserById(SessionManager.getUserSession(this));
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


    static Intent intentFactory(Context context){
        return new Intent(context, Settings_page.class);

    }
}