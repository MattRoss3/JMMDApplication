package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityMainUserInterfaceBinding;
import com.example.jmmdapplication.databinding.ActivitySettingsPageBinding;

/**
 * The settings page activity of the application.
 * <p>
 * This activity provides options for users to configure their settings. It allows users to interact with settings-related UI elements.
 * </p>
 */

public class Settings_page extends AppCompatActivity {
    private ActivitySettingsPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);
        binding=ActivitySettingsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent= MainUserInterface.intentFactory(getApplicationContext());
//                startActivity(intent);
            }
        });
    }

    /**
     * Creates an intent for starting the {@link Settings_page} activity.
     *
     * @param context The context to use for creating the intent.
     * @return An intent for starting the {@link Settings_page} activity.
     */
    static Intent intentFactory(Context context){
        return new Intent(context, Settings_page.class);

    }
}