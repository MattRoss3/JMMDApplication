package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;

public class Settings_page extends AppCompatActivity {
    private ActivitySettingsPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    static Intent intentFactory(Context context){
        return new Intent(context, Settings_page.class);

    }
}