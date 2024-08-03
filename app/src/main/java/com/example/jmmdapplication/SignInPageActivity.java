package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.databinding.ActivitySignInPageBinding;


public class SignInPageActivity extends AppCompatActivity {
private ActivitySignInPageBinding binding;
    private static final int USER_ID = 0; // Use 0 as the hardcoded user ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButtonSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignIn();
            }
        });
    }

    private void handleSignIn() {
        String username = binding.userNameSignInScreenEditText.getText().toString();
        String password = binding.PasswordSignInEditText.getText().toString();

        if(validateCredentials(username, password)) {
            Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext(), USER_ID);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateCredentials(String username, String password) {
        //TODO: Replace with the actual validation logic
        return "user".equals(username) && "password".equals(password);
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, SignInPageActivity.class);
    }

}