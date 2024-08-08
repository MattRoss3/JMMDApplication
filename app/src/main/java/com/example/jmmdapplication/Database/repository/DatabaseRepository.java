package com.example.jmmdapplication.Database.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.DAO.AnswerDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.DAO.UserChallengeDAO;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.Relations.QuestionWithAnswers;
import com.example.jmmdapplication.Database.Relations.UsersWithChallenges;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.entities.User;

import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.entities.Challenge;

import com.example.jmmdapplication.Database.DAO.ProgressDAO;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.UserChallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class DatabaseRepository {

    private final UserDAO userDAO;
    private final ChallengeDAO challengeDAO;
    private final UserChallengeDAO userChallengeDAO;
    private final ProgressDAO progressDAO;
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;
    private final ExecutorService executorService;
    private static final String TAG = "MainActivity";
    private static DatabaseRepository repository;

    public DatabaseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        challengeDAO = db.challengeDAO();
        userChallengeDAO = db.userChallengeDAO();
        progressDAO = db.progressDAO();
        questionDAO = db.questionDAO();
        answerDAO = db.answerDAO();
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

    public LiveData<User> getUserById(int userId) {
        return userDAO.getUserById(userId);
    }


    public LiveData<User> getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
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

    public LiveData<List<User>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public LiveData<User> getUserByUsernameAndPassword(String username, String password) {
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public void insertChallenge(Challenge challenge) {
        executorService.execute(() -> {
            challengeDAO.insertChallenge(challenge);
            Log.i(TAG, "Inserted challenge: " + challenge.getName());
        });
    }


    public LiveData<List<Challenge>> getAllChallenges() {
        return challengeDAO.getAllChallenges();
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

    public void assignChallengeToUser(int userId, int challengeId) {
        executorService.execute(() -> {
                userChallengeDAO.insertUserChallenge(new UserChallenge(userId, challengeId));
                Log.i(TAG, "Assigned challengeId: " + challengeId + " to userId: " + userId);
        });
    }

    public void unassignChallengeFromUser(int userId, int challengeId) {
        executorService.execute(() -> {
            userChallengeDAO.deleteUserChallenge(userId, challengeId);
            Log.i(TAG, "Unassigned challengeId: " + challengeId + " from userId: " + userId);
        });
    }

    public LiveData<UsersWithChallenges> getChallengesAssignedToUser(int userId) {
        return userChallengeDAO.getChallengesAssignedToUser(userId);
    }

    public LiveData<List<UsersWithChallenges>> getChallengesNotAssignedToUser(int userId) {
        return userChallengeDAO.getChallengesNotAssignedToUser(userId);
    }


    // Progress-related methods
    public void insertProgress(Progress progress) {
        executorService.execute(() -> {
            progressDAO.insertProgress(progress);
            Log.i(TAG, "Inserted progress for userId: " + progress.getUserId());
        });
    }

    public LiveData<List<Progress>> getProgressByUserId(int userId) {
        return progressDAO.getProgressByUserId(userId);
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

    public void insertQuestion(Question question) {
        executorService.execute(() -> {
            questionDAO.insertQuestion(question);
            Log.i(TAG, "Inserted question: " + question.getQuestionText());
        });
    }

    public LiveData<List<Question>> getQuestionsByChallengeId(int challengeId) {
        return questionDAO.getQuestionsByChallengeId(challengeId);
    }

    public void updateQuestion(Question question) {
        executorService.execute(() -> {
            questionDAO.updateQuestion(question);
            Log.i(TAG, "Updated question: " + question.getQuestionText());
        });
    }

    public void deleteQuestion(Question question) {
        executorService.execute(() -> {
            questionDAO.deleteQuestion(question);
            Log.i(TAG, "Deleted question: " + question.getQuestionText());
        });
    }

    public void insertAnswer(Answer answer) {
        executorService.execute(() -> {
            answerDAO.insertAnswer(answer);
            Log.i(TAG, "Inserted answer for questionId: " + answer.getQuestionId());
        });
    }

    public LiveData<List<QuestionWithAnswers>> getAnswersByQuestionId(int questionId) {
        return answerDAO.getAnswersByQuestionId(questionId);
    }

    public void updateAnswer(Answer answer) {
        executorService.execute(() -> {
            answerDAO.updateAnswer(answer);
            Log.i(TAG, "Updated answer for questionId: " + answer.getQuestionId());
        });
    }

    public void deleteAnswer(Answer answer) {
        executorService.execute(() -> {
            answerDAO.deleteAnswer(answer);
            Log.i(TAG, "Deleted answer for questionId: " + answer.getQuestionId());
        });
    }


}

