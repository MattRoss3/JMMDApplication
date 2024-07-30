package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "progress",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Challenge.class, parentColumns = "id", childColumns = "challengeId", onDelete = ForeignKey.CASCADE)
        })
public class Progress {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    private Integer userId;

    @NonNull
    private Integer challengeId;

    @NonNull
    private String status;

    private Date completionDate;

    private int level;

    public Progress(int userId, int challengeId, @NonNull String status, Date completionDate, int level) {
        this.userId = userId;
        this.challengeId = challengeId;
        this.status = status;
        this.completionDate = completionDate;
        this.level = level;
        id = 0;
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

    public Date getCompletionDate() { return completionDate; }
    public void setCompletionDate(Date completionDate) { this.completionDate = completionDate; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
}
