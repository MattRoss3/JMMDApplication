package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.Relations.ChallengeWithDetails;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.databinding.ActivityAddnewChallengeBinding;
import com.example.jmmdapplication.util.SessionManager;

import java.util.ArrayList;

/**
 * Activity that allows users to add a new challenge.
 * <p>
 * This activity provides an interface for users to input details for a new challenge.
 * It includes a button to navigate back to the main user interface.
 * </p>
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 */
public class AddNewChallenge extends AppCompatActivity {
    private ActivityAddnewChallengeBinding binding;
    private DatabaseRepository repository=DatabaseRepository.getRepository(this.getApplication());
    private Challenge chal=null;
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
        int userId = SessionManager.getUserSession(this);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
        binding.SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 chal=displayChallenge(binding.enterLanguageBar.getText().toString());
            }
        });

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chal != null) {
                    for (Challenge challenge : repository.getAllChallenges()) {
                        if (challenge == chal) {
                            if (chal.isAssigned()) {
                                Toast.makeText(AddNewChallenge.this, "Challenge already added", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            chal.setAssigned(true);
                        }
                    }

                }
                else{
                    Toast.makeText(AddNewChallenge.this, "No Challenge to add", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }
    private Challenge displayChallenge(String language){
        int check=0;

        for(Challenge challenge:repository.getAllChallenges()) {
            if (challenge.getCategory().equalsIgnoreCase(language)) {
                String temp = challenge.getCategory() + "\n" + challenge.getDescription();
                binding.button1.setText(temp);
                binding.button1.setVisibility(View.VISIBLE);
                return challenge;
            }
        }
            Toast.makeText(this, "Language not Found", Toast.LENGTH_SHORT).show();
            return null;
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