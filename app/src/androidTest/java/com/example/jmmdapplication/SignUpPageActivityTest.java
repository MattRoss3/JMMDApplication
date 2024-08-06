package com.example.jmmdapplication;

import android.widget.Toast;

import androidx.arch.core.executor.TaskExecutor;
import androidx.test.core.app.ApplicationProvider;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.util.SessionManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SignUpPageActivityTest {


    @Mock
    private DatabaseRepository mockRepository;

    @InjectMocks
    private SignUpPageActivity signUpPageActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp_Successful() {
        String username = "newuser";
        String password = "password123";
        String confirmPassword = "password123";

        when(mockRepository.getUserByUsername(username)).thenReturn(null);

        // Set up the mocked views
        signUpPageActivity.binding.usernameSignUp.setText(username);
        signUpPageActivity.binding.password1SignUp.setText(password);
        signUpPageActivity.binding.password2SignUp.setText(confirmPassword);

        // Call the method to test
        signUpPageActivity.signUp();

        // Verify interactions and results
        verify(mockRepository).insertUser(any(User.class));
        verify(mockRepository).getUserByUsername(username);
    }

    @Test
    public void testSignUp_PasswordMismatch() {
        String username = "user";
        String password = "password";
        String confirmPassword = "differentpassword";

        // Set up the mocked views
        signUpPageActivity.binding.usernameSignUp.setText(username);
        signUpPageActivity.binding.password1SignUp.setText(password);
        signUpPageActivity.binding.password2SignUp.setText(confirmPassword);

        // Call the method to test
        signUpPageActivity.signUp();

        // Verify that no user is inserted due to password mismatch
        verify(mockRepository, never()).insertUser(any(User.class));
    }

    @Test
    public void testSignUp_UsernameAlreadyTaken() {
        String username = "existinguser";
        String password = "password";
        String confirmPassword = "password";

        when(mockRepository.getUserByUsername(username)).thenReturn(new User(username, password, false));

        // Set up the mocked views
        signUpPageActivity.binding.usernameSignUp.setText(username);
        signUpPageActivity.binding.password1SignUp.setText(password);
        signUpPageActivity.binding.password2SignUp.setText(confirmPassword);

        // Call the method to test
        signUpPageActivity.signUp();

        // Verify that no new user is inserted
        verify(mockRepository, never()).insertUser(any(User.class));
    }
}
