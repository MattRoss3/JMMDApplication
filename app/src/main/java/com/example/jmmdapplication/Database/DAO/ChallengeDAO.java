package com.example.jmmdapplication.Database.DAO;

import androidx.room.*;

import com.example.jmmdapplication.Database.entities.Challenge;

import java.util.List;


@Dao
public interface ChallengeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertChallenge(Challenge challenge);

    @Query("SELECT * FROM challenges")
    List<Challenge> getAllChallenges();

    @Update
    void updateChallenge(Challenge challenge);

    @Delete
    void deleteChallenge(Challenge challenge);
}
