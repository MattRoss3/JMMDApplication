package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import java.util.List;

public class ProgressViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;

    public ProgressViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
    }

    public void insert(Progress progress) {
        if (progress != null) {
            repository.insertProgress(progress);
        }
    }

    public void update(Progress progress) {
        if (progress != null) {
            repository.updateProgress(progress);
        }
    }

    public void delete(Progress progress) {
        if (progress != null) {
            repository.deleteProgress(progress);
        }
    }

    public LiveData<List<Progress>> getProgressByUserId(int userId) {
        return repository.getProgressByUserId(userId);
    }
}
