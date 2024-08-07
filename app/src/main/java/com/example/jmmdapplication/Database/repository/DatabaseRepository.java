package com.example.jmmdapplication.Database.repository;

import android.app.Application;
import android.util.Log;

import com.example.jmmdapplication.Database.AppDatabase;
import com.example.jmmdapplication.Database.DAO.AnswerDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.Relations.UserWithDetails;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Question;
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
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;
    private final ExecutorService executorService;
    private static final String TAG = "MainActivity";
    private static DatabaseRepository repository;

    public DatabaseRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        userDAO = db.userDAO();
        challengeDAO = db.challengeDAO();
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

    public List<UserWithDetails> getUsersWithDetails() {
        Future<List<UserWithDetails>> future = executorService.submit(new Callable<List<UserWithDetails>>() {
            @Override
            public List<UserWithDetails> call() throws Exception {
                return userDAO.getUsersWithDetails();
            }
        });

        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error fetching users with details", e);
            return null;
        }
    }


    public UserWithDetails getUserWithDetails(int userId) {
        Future<UserWithDetails> future = executorService.submit(new Callable<UserWithDetails>() {
            @Override
            public UserWithDetails call() throws Exception {
                return userDAO.getUserWithDetails(userId);
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error fetching user with details", e);
            return null;
        }
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

    public User getUserByUsernameAndPassword(String username, String password) {
        Future<User> future = executorService.submit(() -> userDAO.getUserByUsernameAndPassword(username, password));
        try {
            User user = future.get();
            Log.i(TAG, "Fetched user with username: " + username);
            return user;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting user by username and password", e);
        }
        return null;
    }

    public void insertChallenge(Challenge challenge) {
        executorService.execute(() -> {
            challengeDAO.insertChallenge(challenge);
            Log.i(TAG, "Inserted challenge: " + challenge.getName());
        });
    }

    public List<Challenge> getChallengesByUserId(int userId) {
        return challengeDAO.getChallengesByUserId(userId);
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
        Future<List<Progress>> future = executorService.submit(new Callable<List<Progress>>() {
            @Override
            public List<Progress> call() throws Exception {
                return progressDAO.getProgressByUserId(userId);
            }
        });

        try {
            ArrayList<Progress> progressList = (ArrayList<Progress>) future.get();
            Log.i(TAG, "Fetched progress for userId: " + userId);
            return progressList;
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

    public void insertQuestion(Question question) {
        executorService.execute(() -> {
            questionDAO.insertQuestion(question);
            Log.i(TAG, "Inserted question: " + question.getQuestionText());
        });
    }

    public ArrayList<Question> getQuestionsByChallengeId(int challengeId) {
        Future<List<Question>> future = executorService.submit(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return questionDAO.getQuestionsByChallengeId(challengeId);
            }
        });

        try {
            ArrayList<Question> questions = (ArrayList<Question>) future.get();
            Log.i(TAG, "Fetched questions for challengeId: " + challengeId);
            return questions;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting questions by challengeId", e);
        }
        return new ArrayList<>();
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

    public ArrayList<Answer> getAnswersByQuestionId(int questionId) {
        Future<List<Answer>> future = executorService.submit(new Callable<List<Answer>>() {
            @Override
            public List<Answer> call() throws Exception {
                return answerDAO.getAnswersByQuestionId(questionId);
            }
        });

        try {
            ArrayList<Answer> answers = (ArrayList<Answer>) future.get();
            Log.i(TAG, "Fetched answers for questionId: " + questionId);
            return answers;
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, "Error getting answers by questionId", e);
        }
        return new ArrayList<>();
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

