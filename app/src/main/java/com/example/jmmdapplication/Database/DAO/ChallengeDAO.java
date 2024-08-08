package com.example.jmmdapplication.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.jmmdapplication.Database.entities.Challenge;

import java.util.List;


@Dao
public interface ChallengeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertChallenge(Challenge challenge);

    @Query("SELECT * FROM challenges WHERE name = :name")
    Challenge getChallengeByNameSync(String name);


    @Query("SELECT * FROM challenges")
    LiveData<List<Challenge>> getAllChallenges();

    @Update
    void updateChallenge(Challenge challenge);

    @Delete
    void deleteChallenge(Challenge challenge);
}
