package com.example.jmmdapplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.example.jmmdapplication.Database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Local unit test for class UserDAO.java, which will execute on the development machine (host).
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 * @authors Jerrick Wallace, Matthew Ross, Mohamed Othman, Dakota Fouch
 * @since 08/07/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */
@RunWith(JUnit4.class)
public class UserEntityUnitTest {
    User user;
    String username;
    String password;
    boolean isAdmin;

    User user1;
    String username1;
    String password1;
    boolean isAdmin1;

    User user2;
    String username2;
    String password2;
    boolean isAdmin2;

    User user3;
    String username3;
    String password3;
    boolean isAdmin3;

    User user4;
    String username4;
    String password4;
    boolean isAdmin4;


    //CONSTRUCTOR and DESTRUCTOR
    @Before
    public void setUp() {

        user = null;
        assertNull(user);

        username = "jerrick";
        password = "password";
        isAdmin = true;
        user = new User(username, password, isAdmin);
        assertNotNull(user);

        username1 = "matthew";
        password1 = "password1";
        isAdmin1 = false;
        user1 = new User(username1, password1, isAdmin1);
        assertNotNull(user1);

        username2 = "mohamed";
        password2 = "password2";
        isAdmin2 = true;
        user2 = new User(username2, password2, isAdmin2);
        assertNotNull(user2);

        user3 = null;
        assertNull(user3);
        user3 = new User("dakota", "password3", false);
        assertNotNull(user3);

        username3 = "dakota";
        password3 = "password3";
        isAdmin2 = true;

        user4 = null;
        assertNull(user4);
    }

    @After
    public void tearDown() {
        user = null;
        username = null;
        password = null;

        user1 = null;
        username1 = null;
        password1 = null;

        user2 = null;
        username2 = null;
        password2 = null;

        user3 = null;
        username3 = null;
        password3 = null;

        user4 = null;
        username4 = null;
        password4 = null;
    }

    @Test
    public void getUserIdTest() {

        assertNotNull(user);
        assertEquals(0, user.getUserId());

        assertNotNull(user1);
        assertEquals(0, user1.getUserId());

        assertNotNull(user2);
        assertEquals(0, user2.getUserId());

        assertNotNull(user3);
        assertEquals(0, user3.getUserId());

        assertNull(user4);
    }

    @Test
    public void setUserIdTest() {
        assertNotNull(user);
        assertEquals(0, user.getUserId());
        user.setUserId(10);

        assertNotNull(user1);
        assertEquals(0, user1.getUserId());
        user1.setUserId(100);

        assertNotNull(user2);
        assertEquals(0, user2.getUserId());
        user2.setUserId(1000);

        assertNotNull(user3);
        assertEquals(0, user3.getUserId());
        user3.setUserId(10000);

        assertNotNull(user);
        assertEquals(10, user.getUserId());

        assertNotNull(user1);
        assertEquals(100, user1.getUserId());

        assertNotNull(user2);
        assertEquals(1000, user2.getUserId());

        assertNotNull(user3);
        assertEquals(10000, user3.getUserId());
    }

    @Test
    public void getUsernameTest() {
        assertNotNull(user);
        assertEquals(username, user.getUsername());

        assertNotNull(user1);
        assertEquals(username1, user1.getUsername());

        assertNotNull(user2);
        assertEquals(username2, user2.getUsername());

        assertNotNull(user3);
        assertEquals(username3, user3.getUsername());

        assertNull(user4);
    }
}



