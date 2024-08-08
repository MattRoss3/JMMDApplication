package com.example.jmmdapplication.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.UserChallenge;

import java.util.List;

@Dao
public interface UserChallengeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserChallenge(UserChallenge userChallenge);

    @Update
    void updateUserChallenge(UserChallenge userChallenge);

    @Query("DELETE FROM userChallenges WHERE userId = :userId AND challengeId = :challengeId")
    void deleteUserChallenge(int userId, int challengeId);

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    LiveData<UsersWithChallenges> getChallengesAssignedToUser(int userId);

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId AND userId NOT IN (SELECT userId FROM userChallenges)")
    LiveData<List<UsersWithChallenges>> getChallengesNotAssignedToUser(int userId);
}
