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

import com.example.jmmdapplication.databinding.ActivitySignInPageBinding;

public class signInPageActivity extends AppCompatActivity {

    private ActivitySignInPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignInPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButtonSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignIn();
            }
        });

        binding.signUpButtonSignInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignUp();
            }
        });

    }

    private void handleSignIn() {
        String username = binding.userNameSignInScreenEditText.getText().toString();
        String password = binding.PasswordSignInEditText.getText().toString();

        if(validateCredentials(username,password)) {

            Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(this);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateCredentials(String username, String password) {
        return "user".equals(username) && "password".equals(password);
    }

    static Intent intentFactory(Context context){
        return new Intent(context, signInPageActivity.class);
    }

}