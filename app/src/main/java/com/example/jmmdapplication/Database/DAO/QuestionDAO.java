package com.example.jmmdapplication.Database.DAO;

import androidx.room.*;

import com.example.jmmdapplication.Database.entities.Question;

import java.util.List;

@Dao
public interface QuestionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertQuestion(Question question);

    @Query("SELECT * FROM questions WHERE challengeId = :challengeId")
    List<Question> getQuestionsByChallengeId(int challengeId);

    @Update
    void updateQuestion(Question question);

    @Delete
    void deleteQuestion(Question question);
}
