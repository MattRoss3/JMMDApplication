package com.example.jmmdapplication.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Question;

import java.util.List;

public class ChallengeWithQuestions {
    @Embedded
    public Challenge challenge;
    @Relation(
            parentColumn = "challengeId",
            entityColumn = "challengeId"
    )
    public List<Question> questions;
}
