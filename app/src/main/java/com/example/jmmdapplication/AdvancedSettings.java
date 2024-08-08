package com.example.jmmdapplication;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityAdvancedSettingsBinding;

public class AdvancedSettings extends AppCompatActivity {
    ActivityAdvancedSettingsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdvancedSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.funbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}