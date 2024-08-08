package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.Relations.QuestionWithAnswers;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import java.util.List;

public class AnswerViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;

    public AnswerViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
    }

    public void insert(Answer answer) {
        if (answer != null) {
            repository.insertAnswer(answer);
        }
    }

    public void update(Answer answer) {
        if (answer != null) {
            repository.updateAnswer(answer);
        }
    }

    public void delete(Answer answer) {
        if (answer != null) {
            repository.deleteAnswer(answer);
        }
    }

    public LiveData<List<QuestionWithAnswers>> getAnswersByQuestionId(int questionId) {
        return repository.getAnswersByQuestionId(questionId);
    }
}
