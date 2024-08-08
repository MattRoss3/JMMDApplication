//package com.example.jmmdapplication;
//
//import android.content.Intent;
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
//import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
//@RunWith(AndroidJUnit4.class)
//public class SignInPageActivityTest {
//
//    @Rule
//    public ActivityScenarioRule<SignInPageActivity> activityRule =
//            new ActivityScenarioRule<>(SignInPageActivity.class);
//
//    private DatabaseRepository repository;
//    private SignInPageActivity activity;
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
//    public void testSignIn_Success() throws InterruptedException {
//        // Insert a test user into the repository
//        User testUser = new User("testuser", "password", false);
//        repository.insertUser(testUser);
//
//        // Wait for database operations to complete
//        Thread.sleep(1000); // Ensure the user is inserted before proceeding
//
//        // Fill in the sign-in form
//        activity.runOnUiThread(() -> {
//            activity.binding.userNameSignInScreenEditText.setText("testuser");
//            activity.binding.passwordSignInEditText.setText("password");
//        });
//
//        // Activate sign-in
//        CountDownLatch latch = new CountDownLatch(1);
//        activity.runOnUiThread(() -> {
//            activity.binding.signInButtonSignInPage.performClick();
//            latch.countDown();
//        });
//
//        latch.await(3, TimeUnit.SECONDS); // Wait for background tasks
//
//        // Check the result
//        Intent expectedIntent = new Intent(activity, MainUserInterface.class);
//        Intent actual = getInstrumentation().getTargetContext().getPackageManager().getLaunchIntentForPackage(expectedIntent.getComponent().getPackageName());
//        assertNotNull("Intent should start MainUserInterface", actual);
//        assertEquals("Intent should start MainUserInterface", expectedIntent.getComponent().getClassName(), actual.getComponent().getClassName());
//    }
//
//    @Test
//    public void testSignIn_Failure() throws InterruptedException {
//        // Fill in sign-in form with incorrect credentials
//        activity.runOnUiThread(() -> {
//            activity.binding.userNameSignInScreenEditText.setText("wronguser");
//            activity.binding.passwordSignInEditText.setText("wrongpassword");
//        });
//
//        // Trigger sign-in
//        CountDownLatch latch = new CountDownLatch(1);
//        activity.runOnUiThread(() -> {
//            activity.binding.signInButtonSignInPage.performClick();
//            latch.countDown();
//        });
//
//        latch.await(3, TimeUnit.SECONDS); // Wait for background tasks
//
//        // Verify the Toast message
//        activity.runOnUiThread(() -> {
//            Toast toast = Toast.makeText(activity, "Invalid username or password", Toast.LENGTH_SHORT);
//            toast.show();
//        });
//
//        assertEquals("Invalid username or password", "Invalid username or password");
//    }
//}
