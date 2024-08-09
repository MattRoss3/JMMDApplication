package com.example.jmmdapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.util.SessionManager;


/**
 * This is the launcher activity that will determine if the user is logged in or not.
 * If the user is logged in, it will redirect to the main user interface.
 * If the user is not logged in, it will redirect to the sign-in page.
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 * @author Mohamed Othman
 * @since 07/20/2024
 * CST 338 Software Design with Dr. C
 * wk05: Project 2
 * Fix git
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int loggedInUserId = SessionManager.getUserSession(this);

        Intent intent;

        if (loggedInUserId == -1) {
            intent = new Intent(this, SignInPageActivity.class);
        } else {
            intent = new Intent(this, MainUserInterface.class);
        }
        startActivity(intent);
    }
}
