package com.example.jmmdapplication.Database.repository;

import android.app.Application;
import android.util.Log;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.entities.User;

import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.entities.Challenge;

import com.example.jmmdapplication.Database.DAO.ProgressDAO;
import com.example.jmmdapplication.Database.entities.Progress;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DatabaseRepository {

    private final UserDAO userDAO;
    private final ChallengeDAO challengeDAO;
    private final ProgressDAO progressDAO;
    private final ExecutorService executorService;
    private static final String TAG = "MainActivity";
    private static DatabaseRepository repository;

    public DatabaseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        challengeDAO = db.challengeDAO();
        progressDAO = db.progressDAO();
        executorService = AppDatabase.databaseWriteExecutor;

    }

    public void insertUser(User user) {
        executorService.execute(() -> {
            userDAO.insertUser(user);
            Log.i(TAG, "Inserted user: " + user.getUsername());
        });
    }

    public static DatabaseRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<DatabaseRepository> future = AppDatabase.databaseWriteExecutor.submit(new Callable<DatabaseRepository>() {
            @Override
            public DatabaseRepository call() throws Exception {
                return new DatabaseRepository(application);
            }
        });

        try {
            DatabaseRepository repository = future.get();
            Log.i(TAG, "Created repository");
            return repository;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error creating repository", e);
        }
        return null;
    }


    public User getUserByUsername(String username) {
        Future<User> future = executorService.submit(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return userDAO.getUserByUsername(username);
            }
        });

        try {
            User user = future.get();
            Log.i(TAG, "Fetched user: " + username);
            return user;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting user by username", e);
        }
        return null;
    }

    public void updateUser(User user) {
        executorService.execute(() -> {
            userDAO.updateUser(user);
            Log.i(TAG, "Updated user: " + user.getUsername());
        });
    }

    public void deleteUser(User user) {
        executorService.execute(() -> {
            userDAO.deleteUser(user);
            Log.i(TAG, "Deleted user: " + user.getUsername());
        });
    }

    public ArrayList<User> getAllUsers() {
        Future<ArrayList<User>> future = executorService.submit(new Callable<ArrayList<User>>() {
            @Override
            public ArrayList<User> call() throws Exception {
                return (ArrayList<User>) userDAO.getAllUsers();
            }
        });

        try {
            ArrayList<User> users = future.get();
            Log.i(TAG, "Fetched all users: " + users.size() + " users found.");
            return users;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting all users", e);
        }
        return null;
    }

    public void insertChallenge(Challenge challenge) {
        executorService.execute(() -> {
            challengeDAO.insertChallenge(challenge);
            Log.i(TAG, "Inserted challenge: " + challenge.getName());
        });
    }

    public ArrayList<Challenge> getAllChallenges() {
        Future<List<Challenge>> future = executorService.submit(new Callable<List<Challenge>>() {
            @Override
            public ArrayList<Challenge> call() throws Exception {
                return (ArrayList<Challenge>)challengeDAO.getAllChallenges();
            }
        });

        try {
            ArrayList<Challenge> challenges = (ArrayList<Challenge>) future.get();
            Log.i(TAG, "Fetched all challenges: " + challenges.size() + " challenges found.");
            return challenges;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting all challenges", e);
        }
        return new ArrayList<>();
    }

    public void updateChallenge(Challenge challenge) {
        executorService.execute(() -> {
            challengeDAO.updateChallenge(challenge);
            Log.i(TAG, "Updated challenge: " + challenge.getName());
        });
    }

    public void deleteChallenge(Challenge challenge) {
        executorService.execute(() -> {
            challengeDAO.deleteChallenge(challenge);
            Log.i(TAG, "Deleted challenge: " + challenge.getName());
        });
    }

    // Progress-related methods
    public void insertProgress(Progress progress) {
        executorService.execute(() -> {
            progressDAO.insertProgress(progress);
            Log.i(TAG, "Inserted progress for userId: " + progress.getUserId());
        });
    }

    public ArrayList<Progress> getProgressByUserId(int userId) {
        Future<List<Progress>> future = executorService.submit(() -> progressDAO.getProgressByUserId(userId));

        try {
            List<Progress> progressList = future.get();
            Log.i(TAG, "Fetched progress for userId: " + userId);
            return new ArrayList<>(progressList);
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting progress by userId", e);
        }
        return new ArrayList<>();
    }

    public void updateProgress(Progress progress) {
        executorService.execute(() -> {
            progressDAO.updateProgress(progress);
            Log.i(TAG, "Updated progress for userId: " + progress.getUserId());
        });
    }

    public void deleteProgress(Progress progress) {
        executorService.execute(() -> {
            progressDAO.deleteProgress(progress);
            Log.i(TAG, "Deleted progress for userId: " + progress.getUserId());
        });
    }
}

