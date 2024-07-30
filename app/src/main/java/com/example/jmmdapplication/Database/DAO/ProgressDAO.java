package com.example.jmmdapplication.Database.DAO;

import androidx.room.*;

import com.example.jmmdapplication.Database.entities.Progress;

import java.util.List;

@Dao
public interface ProgressDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProgress(Progress progress);

    @Query("SELECT * FROM progress WHERE userId = :userId")
    List<Progress> getProgressByUserId(int userId);

    @Update
    void updateProgress(Progress progress);

    @Delete
    void deleteProgress(Progress progress);
}