package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.databinding.ActivityAddnewChallengeBinding;
/**
 * Activity that allows users to add a new challenge.
 * <p>
 * This activity provides an interface for users to input details for a new challenge.
 * It includes a button to navigate back to the main user interface.
 * </p>
 */
public class AddNewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;

    /**
     * Called when the activity is first created.
     * <p>
     * This method sets up the UI by inflating the layout and setting up click listeners.
     * </p>
     *
     * @param savedInstanceState If the activity is being re-created from a previous saved state, this bundle contains the data it most recently supplied in {@link #onSaveInstanceState(Bundle)}. <b>Note:</b> Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_challenge);
        binding=ActivityAddnewChallengeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /**
     * Factory method to create an {@link Intent} for starting {@link AddNewChallenge}.
     *
     * @param context The context from which the intent will be started.
     * @return An {@link Intent} that can be used to start {@link AddNewChallenge}.
     */
    static Intent intentFactory(Context context){
        return new Intent(context, AddNewChallenge.class);
    }
}