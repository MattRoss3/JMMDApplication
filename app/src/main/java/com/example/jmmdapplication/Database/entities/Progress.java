package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.time.LocalDateTime;

@Entity(tableName = "progress",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Challenge.class, parentColumns = "id", childColumns = "challengeId", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index(value = "userId"), @Index(value = "challengeId")})
public class Progress {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;

    private int challengeId;

    @NonNull
    private String status;

    private LocalDateTime completionDate;

    private int level;

    public Progress(int userId, int challengeId, @NonNull String status, LocalDateTime completionDate, int level) {
        this.userId = userId;
        this.challengeId = challengeId;
        this.status = status;
        this.completionDate = completionDate;
        this.level = level;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getChallengeId() { return challengeId; }
    public void setChallengeId(int challengeId) { this.challengeId = challengeId; }

    @NonNull
    public String getStatus() { return status; }
    public void setStatus(@NonNull String status) { this.status = status; }

    public LocalDateTime getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDateTime completionDate) { this.completionDate = completionDate; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
