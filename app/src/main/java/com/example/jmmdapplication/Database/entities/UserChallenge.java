package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "userChallenges",
        primaryKeys = {"userId", "challengeId"})

public class UserChallenge {
    public int userId;
    public int challengeId;

    // Constructor
    public UserChallenge(int userId, int challengeId) {
        this.userId = userId;
        this.challengeId = challengeId;
    }

}
