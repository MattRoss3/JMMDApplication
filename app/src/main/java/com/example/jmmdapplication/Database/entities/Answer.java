package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "answers",
        foreignKeys = @ForeignKey(entity = Question.class,
                parentColumns = "questionId",
                childColumns = "questionId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "questionId")})
public class Answer {
    @PrimaryKey(autoGenerate = true)
    private int answerId;

    private int questionId;

    @NonNull
    private String answerText;

    public Answer(int questionId, @NonNull String answerText) {
        this.questionId = questionId;
        this.answerText = answerText;
    }

    public int getAnswerId() { return answerId; }
    public void setAnswerId(int answerId) { this.answerId = answerId; }

    public int getQuestionId() { return questionId; }
    public void setQuestionId(int questionId) { this.questionId = questionId; }

    @NonNull
    public String getAnswerText() { return answerText; }
    public void setAnswerText(@NonNull String answerText) { this.answerText = answerText; }
}
