package com.example.jmmdapplication.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.jmmdapplication.Database.Relations.QuestionWithAnswers;
import com.example.jmmdapplication.Database.entities.Answer;

import java.util.List;

@Dao
public interface AnswerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnswer(Answer answer);

    @Transaction
    @Query("SELECT * FROM questions WHERE questionId = :questionId")
    LiveData<List<QuestionWithAnswers>> getAnswersByQuestionId(int questionId);

    @Update
    void updateAnswer(Answer answer);

    @Delete
    void deleteAnswer(Answer answer);
}