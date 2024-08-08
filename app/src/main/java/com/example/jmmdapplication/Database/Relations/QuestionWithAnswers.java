package com.example.jmmdapplication.Database.Relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Question;

import java.util.List;

public class QuestionWithAnswers {
    @Embedded public Question question;
    @Relation(
            parentColumn = "questionId",
            entityColumn = "questionId"
    )
    public List<Answer> answers;

}
