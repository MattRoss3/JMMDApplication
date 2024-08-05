package com.example.jmmdapplication.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.entities.Progress;

import java.util.List;
import java.util.Objects;

public class UserWithDetails {
    @Embedded
    public User user;

    @Relation(parentColumn = "userId", entityColumn = "userId", entity = Challenge.class)
    public List<ChallengeWithDetails> challengeWithDetails;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<Progress> progress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithDetails that = (UserWithDetails) o;
        return Objects.equals(user, that.user) && Objects.equals(challengeWithDetails, that.challengeWithDetails) && Objects.equals(progress, that.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, challengeWithDetails, progress);
    }
}
