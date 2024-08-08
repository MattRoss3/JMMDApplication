package com.example.jmmdapplication.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.entities.UserChallenge;

import java.util.List;

public class UsersWithChallenges {
    @Embedded public User user;
    @Relation(
            parentColumn = "userId",
            entityColumn = "challengeId",
            associateBy = @Junction(UserChallenge.class)
    )
    public List<Challenge> challenges;
}
