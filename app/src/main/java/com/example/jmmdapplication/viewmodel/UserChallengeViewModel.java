package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;

import java.util.List;

public class UserChallengeViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;

    public UserChallengeViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
    }

    public void assignChallengeToUser(int userId, int challengeId) {
        repository.assignChallengeToUser(userId, challengeId);
    }

    public void unassignChallengeFromUser(int userId, int challengeId) {
        repository.unassignChallengeFromUser(userId, challengeId);
    }

    public LiveData<UsersWithChallenges> getChallengesAssignedToUser(int userId) {
        return repository.getChallengesAssignedToUser(userId);
    }

    public LiveData<List<UsersWithChallenges>> getChallengesNotAssignedToUser(int userId) {
        return repository.getChallengesNotAssignedToUser(userId);
    }
}
