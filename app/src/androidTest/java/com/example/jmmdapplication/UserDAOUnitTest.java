package com.example.jmmdapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Local unit test for class UserDAO.java, which will execute on the development machine (host).
 *
 * Link to GitHub Repo: <a href="https://github.com/MattRoss3/JMMDApplication">...</a>
 * @authors Jerrick Wallace, Matthew Ross, Mohamed Othman, Dakota Fouch
 * @since 08/05/2024
 * CST 338 Software Design with Dr. C
 * wk07: Project 2
 */
@RunWith(AndroidJUnit4.class)
public class UserDAOUnitTest {
    private UserDAO userDao;
    private AppDatabase database;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = database.userDAO();
    }

    @After
    public void closeDb() {
        database.close();
    }

    @Test
    public void insertUserTest() throws Exception {
        User user = new User("Their Username", "Their Password", false);
        user.setUserId(1);

        assertEquals(0,userDao.getAllUsers().size());

        userDao.insertUser(user);

        assertEquals(1,userDao.getAllUsers().size());
        assertEquals("Their Username", userDao.getUserByUsername("Their Username").getUsername());
    }

    @Test
    public void updateUserTest() throws Exception {
        User user = new User("Their Username", "Their Password", false);
        user.setUserId(1);

        assertEquals(0,userDao.getAllUsers().size());

        userDao.insertUser(user);

        assertEquals(1,userDao.getAllUsers().size());
        assertEquals("Their Username", userDao.getUserByUsername("Their Username").getUsername());
        assertFalse(userDao.getUserByUsername("Their Username").isAdmin());

        User user2 = new User("Their New Username", "Their New Password", true);
        user2.setUserId(1);

        userDao.updateUser(user2);
        assertEquals(1,userDao.getAllUsers().size());
        assertEquals("Their New Username", userDao.getUserByUsername("Their New Username").getUsername());
        assertEquals("Their New Password", userDao.getUserByUsername("Their New Username").getPassword());
        assertTrue(userDao.getUserByUsername("Their New Username").isAdmin());
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = new User("Their Username", "Their Password", false);
        user.setUserId(1);

        assertEquals(0,userDao.getAllUsers().size());

        userDao.insertUser(user);

        assertEquals(1,userDao.getAllUsers().size());
        assertEquals("Their Username", userDao.getUserByUsername("Their Username").getUsername());
        assertFalse(userDao.getUserByUsername("Their Username").isAdmin());

        userDao.deleteUser(user);

        assertEquals(0,userDao.getAllUsers().size());
    }
}
