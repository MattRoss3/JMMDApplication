package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityAdminpageBinding;

public class Adminpage extends AppCompatActivity {
    private ActivityAdminpageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        binding=ActivityAdminpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}