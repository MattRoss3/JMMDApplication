package com.example.jmmdapplication.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.jmmdapplication.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    //get user by id
    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<User> getUserById(int userId);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsernameSync(String username);


    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<User> getUserByUsername(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    LiveData<User> getUserByUsernameAndPassword(String username, String password);



}