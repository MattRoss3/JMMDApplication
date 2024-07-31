package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "questions",
        foreignKeys = @ForeignKey(entity = Challenge.class, parentColumns = "id", childColumns = "challengeId", onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "challengeId")})
public class Question {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int challengeId;

    @NonNull
    private String questionText;

    @NonNull
    private String language;

    public Question(int challengeId, @NonNull String questionText, @NonNull String language) {
        this.challengeId = challengeId;
        this.questionText = questionText;
        this.language = language;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getChallengeId() { return challengeId; }
    public void setChallengeId(int challengeId) { this.challengeId = challengeId; }

    @NonNull
    public String getQuestionText() { return questionText; }
    public void setQuestionText(@NonNull String questionText) { this.questionText = questionText; }

    @NonNull
    public String getLanguage() { return language; }
    public void setLanguage(@NonNull String language) { this.language = language; }
}