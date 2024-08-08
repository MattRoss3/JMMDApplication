package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;

    public QuestionViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
    }

    public void insert(Question question) {
        if (question != null) {
            repository.insertQuestion(question);
        }
    }

    public void update(Question question) {
        if (question != null) {
            repository.updateQuestion(question);
        }
    }

    public void delete(Question question) {
        if (question != null) {
            repository.deleteQuestion(question);
        }
    }

    public LiveData<List<Question>> getQuestionsByChallengeId(int challengeId) {
        return repository.getQuestionsByChallengeId(challengeId);
    }
}
