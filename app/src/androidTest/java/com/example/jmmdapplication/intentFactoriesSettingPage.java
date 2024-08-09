package com.example.jmmdapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;
import com.example.jmmdapplication.viewmodel.UserViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class intentFactoriesSettingPage {
    private UserViewModel userViewModel;
    private UserChallengeViewModel userChallengeViewModel;
    private SettingsPage activity;
    private MutableLiveData<User> userLiveData;
    private MutableLiveData<UsersWithChallenges> usersWithChallengesLiveData;
    @Rule
    public ActivityScenarioRule<SettingsPage> activityRule =
            new ActivityScenarioRule<>(SettingsPage.class);
    @Before
    public void setUp() {

        activityRule.getScenario().onActivity(activity -> {
            this.activity = activity;
            userViewModel = new UserViewModel(ApplicationProvider.getApplicationContext());
            userChallengeViewModel = new UserChallengeViewModel(ApplicationProvider.getApplicationContext());

            // Mocking LiveData
            userLiveData = new MutableLiveData<>();
            usersWithChallengesLiveData = new MutableLiveData<>();

         //   userViewModel.getUserById(activity.userId).observe(activity, userLiveData::setValue);
          //  userChallengeViewModel.getChallengesAssignedToUser(activity.userId).observe(activity, usersWithChallengesLiveData::setValue);
        });
    }
    @Test
    public void testIntentFactory_changeUsername() {
        // Simulate clicking the change usernamebutton
        activity.runOnUiThread(() -> {

            Intent expectedIntent = new Intent(activity, AdvancedSettings.class);
            Intent actual = AdvancedSettings.intentFactory(activity.binding.main.getContext()); // Simplified check
            assertNotNull("Intent should start AddNewChallenge", actual);
            assertEquals("Intent should start AddNewChallenge", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }
    @Test
    public void testIntentFactory_changePassword() {
        // Simulate clicking the changePassword button
        activity.runOnUiThread(() -> {

            Intent expectedIntent = new Intent(activity, changePasswordScreen.class);
            Intent actual = changePasswordScreen.intentFactory(activity.binding.main.getContext()); // Simplified check
            assertNotNull("Intent should start AddNewChallenge", actual);
            assertEquals("Intent should start AddNewChallenge", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }
    @Test
    public void testIntentFactory_backButton() {
        // Simulate clicking the changePassword button
        activity.runOnUiThread(() -> {

            Intent expectedIntent = new Intent(activity, MainUserInterface.class);
            Intent actual = MainUserInterface.MainUserInterfaceIntentFactory(activity.binding.main.getContext()); // Simplified check
            assertNotNull("Intent should start AddNewChallenge", actual);
            assertEquals("Intent should start AddNewChallenge", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }


}
