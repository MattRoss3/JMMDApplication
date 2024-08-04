package com.example.jmmdapplication.Database.DAO;

import androidx.room.*;

import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :id")
    UserWithDetails getUserWithDetails(int id);


    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
    User getUserByUsernameAndPassword(String username, String password);



}