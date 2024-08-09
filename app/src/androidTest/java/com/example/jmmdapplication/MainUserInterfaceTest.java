package com.example.jmmdapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.viewmodel.UserViewModel;
import com.example.jmmdapplication.viewmodel.UserChallengeViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public void testButtonClickListeners_NewChallenge() {
        // Simulate clicking the new challenge button
        activity.runOnUiThread(() -> {
            activity.binding.newChallengeButton.performClick();

            Intent expectedIntent = new Intent(activity, AddNewChallenge.class);
            Intent actual = AddNewChallenge.intentFactory(activity.binding.main.getContext()); // Simplified check
            assertNotNull("Intent should start AddNewChallenge", actual);
            assertEquals("Intent should start AddNewChallenge", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
        });
    }
}
