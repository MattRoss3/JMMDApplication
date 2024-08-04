package com.example.jmmdapplication.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.entities.Progress;

import java.util.List;

public class UserWithDetails {
    @Embedded
    public User user;

    @Relation(parentColumn = "userId", entityColumn = "userId", entity = Challenge.class)
    public List<ChallengeWithDetails> challengeWithDetails;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<Progress> progress;
}
