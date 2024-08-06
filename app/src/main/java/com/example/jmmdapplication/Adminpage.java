package com.example.jmmdapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.jmmdapplication.databinding.ActivityAdminpageBinding;

/**
 * Activity that serves as the admin page in the application.
 * <p>
 * This activity provides the layout and user interface for admin functionalities. Currently, it sets up the view binding for the admin page.
 * </p>
 */

public class Adminpage extends AppCompatActivity {
    private ActivityAdminpageBinding binding;

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the user interface by inflating the layout and initializing the view binding.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}. <b>Note:</b> Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpage);
        binding=ActivityAdminpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}