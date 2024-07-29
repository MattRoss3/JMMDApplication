package com.example.jmmdapplication.Database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "challenges")
public class Challenge {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String description;

    private boolean isAssigned;

    public Challenge(@NonNull String name, @NonNull String description, boolean isAssigned) {
        this.name = name;
        this.description = description;
        this.isAssigned = isAssigned;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @NonNull
    public String getName() { return name; }
    public void setName(@NonNull String name) { this.name = name; }

    @NonNull
    public String getDescription() { return description; }
    public void setDescription(@NonNull String description) { this.description = description; }

    public boolean isAssigned() { return isAssigned; }
    public void setAssigned(boolean assigned) { isAssigned = assigned; }
}
