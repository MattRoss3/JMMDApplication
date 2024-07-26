package com.example.jmmdapplication;

import com.example.jmmdapplication.R;
import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainUserInterface extends AppCompatActivity {
    private ActivityMainUserInterfaceBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainUserInterfaceBinding.inflate(getLayoutInflater());

    }
}