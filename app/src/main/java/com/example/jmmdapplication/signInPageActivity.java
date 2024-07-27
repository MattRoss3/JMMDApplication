package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
    }
    static Intent intentFactory(Context context){
        return new Intent(context, signInPageActivity.class);
    }

}