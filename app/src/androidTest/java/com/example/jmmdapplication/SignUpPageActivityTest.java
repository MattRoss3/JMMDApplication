//package com.example.jmmdapplication;
//
//import android.widget.Toast;
//
//import androidx.test.core.app.ApplicationProvider;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import com.example.jmmdapplication.Database.entities.User;
//import com.example.jmmdapplication.Database.repository.DatabaseRepository;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(AndroidJUnit4.class)
//public class SignUpPageActivityTest {
//
//    @Rule
//    public ActivityScenarioRule<SignUpPageActivity> activityRule =
//            new ActivityScenarioRule<>(SignUpPageActivity.class);
//
//    private DatabaseRepository repository;
//    private SignUpPageActivity activity;
//
//    @Before
//    public void setUp() {
//        activityRule.getScenario().onActivity(activity -> {
//            this.activity = activity;
//            repository = DatabaseRepository.getRepository(ApplicationProvider.getApplicationContext());
//        });
//    }
//
//    @Test
//    public void testSignUp_Success() throws InterruptedException {
//        // Fill in sign-up form
//        activity.runOnUiThread(() -> {
//            activity.binding.usernameSignUp.setText("testuser");
//            activity.binding.password1SignUp.setText("password");
//            activity.binding.password2SignUp.setText("password");
//        });
//
//        // Trigger sign-up
//        CountDownLatch latch = new CountDownLatch(1);
//        activity.runOnUiThread(() -> {
//            activity.binding.signUpButton.performClick();
//            latch.countDown();
//        });
//
//        latch.await(3, TimeUnit.SECONDS); // Increased timeout
//
//        // Check the result
//        User user = repository.getUserByUsername("testuser");
//        //assertNotNull("User should not be null", user); // Improved error message
//        assertEquals("Username should match", "testuser", user.getUsername());
//    }
//
//    @Test
//    public void testSignUp_UsernameTaken() throws InterruptedException {
//        // Insert a user into the repository
//        repository.insertUser(new User("testuser", "password", false));
//
//        // Fill in sign-up form
//        activity.runOnUiThread(() -> {
//            activity.binding.usernameSignUp.setText("testuser");
//            activity.binding.password1SignUp.setText("password");
//            activity.binding.password2SignUp.setText("password");
//        });
//
//        // Trigger sign-up
//        CountDownLatch latch = new CountDownLatch(1);
//        activity.runOnUiThread(() -> {
//            activity.binding.signUpButton.performClick();
//            latch.countDown();
//        });
//
//        latch.await(3, TimeUnit.SECONDS); // Increased timeout
//
//        // Check the result (Toast messages can be difficult to assert, so this test may be indirect)
//        // You might need to use a Toast capturing mechanism or verify indirectly
//    }
//}
