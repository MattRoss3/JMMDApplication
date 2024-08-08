package com.example.jmmdapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.repository.DatabaseRepository;
import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final DatabaseRepository repository;
    private final LiveData<List<User>> allUsers;

    public UserViewModel(Application application) {
        super(application);
        repository = DatabaseRepository.getRepository(application);
        assert repository != null;
        allUsers = repository.getAllUsers();
    }

    public void insert(User user) {
        if (user != null) {
            repository.insertUser(user);
        }
    }

    public void update(User user) {
        if (user != null) {
            repository.updateUser(user);
        }
    }

    public void delete(User user) {
        if (user != null) {
            repository.deleteUser(user);
        }
    }

    public LiveData<User> getUserByUsernameAndPassword(String username, String password) {
        return repository.getUserByUsernameAndPassword(username, password);
    }

    public LiveData<User> getUserByUsername(String username) {
        return repository.getUserByUsername(username);
    }


    public LiveData<User> getUserById(int userId) {
        return repository.getUserById(userId);
    }

    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public LiveData<UsersWithChallenges> getChallengesAssignedToUser(int userId) {
        return repository.getChallengesAssignedToUser(userId);
    }

    public LiveData<List<UsersWithChallenges>> getChallengesNotAssignedToUser(int userId) {
        return repository.getChallengesNotAssignedToUser(userId);
    }
}
