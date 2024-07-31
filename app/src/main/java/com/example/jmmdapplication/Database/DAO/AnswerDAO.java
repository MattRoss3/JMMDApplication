package com.example.jmmdapplication.Database.DAO;

import androidx.room.*;
import com.example.jmmdapplication.Database.entities.Answer;

import java.util.List;

@Dao
public interface AnswerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnswer(Answer answer);

    @Query("SELECT * FROM answers WHERE questionId = :questionId")
    List<Answer> getAnswersByQuestionId(int questionId);

    @Update
    void updateAnswer(Answer answer);

    @Delete
    void deleteAnswer(Answer answer);
}