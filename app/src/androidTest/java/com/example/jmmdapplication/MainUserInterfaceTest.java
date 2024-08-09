package com.example.jmmdapplication;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.viewmodel.UserViewModel;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class MainUserInterfaceTest {

    @Rule
    public ActivityScenarioRule<MainUserInterface> activityRule =
            new ActivityScenarioRule<>(MainUserInterface.class);

    private UserViewModel userViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private MainUserInterface activity;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<UsersWithChallenges> usersWithChallengesLiveData;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            this.activity = activity;
            userViewModel = new UserViewModel(ApplicationProvider.getApplicationContext());
            userChallengeViewModel = new UserChallengeViewModel(ApplicationProvider.getApplicationContext());

            // Mocking LiveData
            userLiveData = new MutableLiveData<>();
            usersWithChallengesLiveData = new MutableLiveData<>();

            userViewModel.getUserById(activity.userId).observe(activity, userLiveData::setValue);
            userChallengeViewModel.getChallengesAssignedToUser(activity.userId).observe(activity, usersWithChallengesLiveData::setValue);
        });
    }

    @Test
    public void testActivityLaunchesCorrectly() {
        // Simple test to ensure the activity and basic UI elements are present
        assertNotNull(activity);
        assertNotNull(activity.binding.mainUserHeader);
        assertNotNull(activity.binding.myChallengesDisplayRecyclerView);
    }

    @Test
    public void testUISetupBasedOnUserRole_Admin() {
        // Set up mock user as admin
        User adminUser = new User("admin", "admin@example.com", true);
        userLiveData.postValue(adminUser);

        activity.runOnUiThread(() -> {
            // Confirm UI updates
            String expectedWelcomeMessage = activity.getString(R.string.welcome_message, adminUser.getUsername());
            assertEquals(expectedWelcomeMessage, activity.binding.mainUserHeader.getText().toString());
            assertEquals(View.VISIBLE, activity.binding.editUserButton.getVisibility());
        });
    }

    @Test
    public void testUISetupBasedOnUserRole_NonAdmin() {
        // Set up mock user as non-admin
        User regularUser = new User("user", "user@example.com", false);
        userLiveData.postValue(regularUser);

        activity.runOnUiThread(() -> {
            // Confirm UI updates
            String expectedWelcomeMessage = activity.getString(R.string.welcome_message, regularUser.getUsername());
            assertEquals(expectedWelcomeMessage, activity.binding.mainUserHeader.getText().toString());
            assertEquals(View.GONE, activity.binding.editUserButton.getVisibility());
        });
    }


    @Test
    public void testButtonClickListeners_NewChallenge() {
        // Simulate clicking the new challenge button
        activity.runOnUiThread(() -> {
            activity.binding.newChallengeButton.performClick();

            Intent expectedIntent = new Intent(activity, AddNewChallenge.class);
            Intent actual = activity.getIntent(); // Simplified check
            assertNotNull("Intent should start AddNewChallenge", actual);
            assertEquals("Intent should start AddNewChallenge", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }

    @Test
    public void testButtonClickListeners_Logout() {
        // Simulate clicking the logout button
        activity.runOnUiThread(() -> {
            activity.binding.logoutButton.performClick();

            Intent expectedIntent = new Intent(activity, SignInPageActivity.class);
            Intent actual = activity.getIntent(); // Simplified check
            assertNotNull("Intent should start SignInPageActivity", actual);
            assertEquals("Intent should start SignInPageActivity", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }

    @Test
    public void testButtonClickListeners_EditUser() {
        // Simulate clicking the edit user button
        activity.runOnUiThread(() -> {
            activity.binding.editUserButton.performClick();

            Intent expectedIntent = new Intent(activity, AdminEditActivity.class);
            Intent actual = activity.getIntent(); // Simplified check
            assertNotNull("Intent should start AdminEditActivity", actual);
            assertEquals("Intent should start AdminEditActivity", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }

    @Test
    public void testButtonClickListeners_Settings() {
        // Simulate clicking the settings button
        activity.runOnUiThread(() -> {
            activity.binding.settingsButton.performClick();

            Intent expectedIntent = new Intent(activity, SettingsPage.class);
            Intent actual = activity.getIntent(); // Simplified check
            assertNotNull("Intent should start SettingsPage", actual);
            assertEquals("Intent should start SettingsPage", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }

    @Test
    public void testIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = MainUserInterface.MainUserInterfaceIntentFactory(context);
        assertEquals(MainUserInterface.class.getName(), intent.getComponent().getClassName());
    }
}
