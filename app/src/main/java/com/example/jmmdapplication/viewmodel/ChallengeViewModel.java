package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import java.util.List;

public class ChallengeViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;
    private final LiveData<List<Challenge>> allChallenges;

    public ChallengeViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
        assert repository != null;
        allChallenges = repository.getAllChallenges();
    }

    public void insert(Challenge challenge) {
        if (challenge != null) {
            repository.insertChallenge(challenge);
        }
    }

    public void update(Challenge challenge) {
        if (challenge != null) {
            repository.updateChallenge(challenge);
        }
    }

    public void delete(Challenge challenge) {
        if (challenge != null) {
            repository.deleteChallenge(challenge);
        }
    }

    public LiveData<List<Challenge>> getAllChallenges() {
        return allChallenges;
    }
}
