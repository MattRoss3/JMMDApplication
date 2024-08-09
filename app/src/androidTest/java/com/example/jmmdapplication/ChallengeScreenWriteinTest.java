package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ChallengeScreenWriteinTest {

    private static final int TEST_USER_ID = 123;
    private static final int TEST_CHALLENGE_ID = 456;
    private static final String TEST_CHALLENGE_NAME = "Test Challenge";
    private static final String TEST_CHALLENGE_DESCRIPTION = "This is a test challenge description.";

    private Context context;

    @Before
    public void setUp() {
        // Prepare the context for use in tests
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testChallengeWriteinIntentFactory() {
        // Generate an intent using the factory method
        Intent intent = ChallengeScreenWritein.ChallengeWriteinIntentFactory(
                context,
                TEST_USER_ID,
                TEST_CHALLENGE_ID,
                TEST_CHALLENGE_NAME,
                TEST_CHALLENGE_DESCRIPTION
        );

        // Verify that the intent has the correct target component
        assertNotNull(intent);
        assertEquals(ChallengeScreenWritein.class.getName(), intent.getComponent().getClassName());

        // Verify that the intent contains the correct extras
        assertEquals(TEST_USER_ID, intent.getIntExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID", -1));
        assertEquals(TEST_CHALLENGE_ID, intent.getIntExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID", -1));
        assertEquals(TEST_CHALLENGE_NAME, intent.getStringExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME"));
        assertEquals(TEST_CHALLENGE_DESCRIPTION, intent.getStringExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION"));
    }

    @Test
    public void testActivityInitialization() {
        // Create an intent with specific extras
        Intent intent = ChallengeScreenWritein.ChallengeWriteinIntentFactory(
                context,
                TEST_USER_ID,
                TEST_CHALLENGE_ID,
                TEST_CHALLENGE_NAME,
                TEST_CHALLENGE_DESCRIPTION
        );

        // Launch the activity with the intent
        try (ActivityScenario<ChallengeScreenWritein> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                // Verify that the activity correctly received and set the data
                assertEquals(TEST_USER_ID, activity.getIntent().getIntExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_USER_ID", -1));
                assertEquals(TEST_CHALLENGE_ID, activity.getIntent().getIntExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_ID", -1));
                assertEquals(TEST_CHALLENGE_NAME, activity.getIntent().getStringExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_NAME"));
                assertEquals(TEST_CHALLENGE_DESCRIPTION, activity.getIntent().getStringExtra("com.example.jmmdapplication.CHALLENGE_ACTIVITY_CHALLENGE_DESCRIPTION"));
            });
        }
    }
}
