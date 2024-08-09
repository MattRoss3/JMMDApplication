package com.example.jmmdapplication.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jmmdapplication.Database.DAO.AnswerDAO;
import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.DAO.ProgressDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.DAO.UserChallengeDAO;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.entities.UserChallenge;
import com.example.jmmdapplication.Database.typeConverters.LocalDataTypeConverter;
import com.example.jmmdapplication.network.OpenAIRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({LocalDataTypeConverter.class})
@Database(entities = {User.class, Challenge.class, UserChallenge.class, Progress.class, Question.class, Answer.class}, version = 13, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "JMMD_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        initializeDatabase(context);
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDAO userDAO();
    public abstract ChallengeDAO challengeDAO();
    public abstract ProgressDAO progressDAO();
    public abstract QuestionDAO questionDAO();
    public abstract AnswerDAO answerDAO();
    public abstract UserChallengeDAO userChallengeDAO();

    // Method to initialize the database
    private static void initializeDatabase(Context context) {
        AppDatabase database = getDatabase(context);
        UserDAO userDao = database.userDAO();
        ChallengeDAO challengeDao = database.challengeDAO();
        QuestionDAO questionDao = database.questionDAO();
        AnswerDAO answerDao = database.answerDAO();
        UserChallengeDAO userChallengeDao = database.userChallengeDAO();

        // Insert admin user
        User admin = new User("admin", "password", true);
        userDao.insertUser(admin);

        // Get the inserted admin user
        User adminUser = userDao.getUserByUsernameSync("admin");
        if (adminUser == null) return;
        int adminId = adminUser.getUserId();

        // Generate and insert challenges using openAI
        retryGenerateAndInsertChallenge("Spanish", "Beginner", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
        retryGenerateAndInsertChallenge("French", "Intermediate", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
        retryGenerateAndInsertChallenge("Russian", "Advanced", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
        retryGenerateAndInsertChallenge("Spanish", "Intermediate", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
        retryGenerateAndInsertChallenge("French", "Advanced", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
        retryGenerateAndInsertChallenge("Russian", "Beginner", 3, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);

    }

    private static void retryGenerateAndInsertChallenge(String language, String level, int numQuestions, int adminId,
                                                        ChallengeDAO challengeDao, QuestionDAO questionDao, AnswerDAO answerDao,
                                                        UserChallengeDAO userChallengeDao, Context context) {
        int maxRetries = 3;
        int attempts = 0;
        boolean success = false;

        while (attempts < maxRetries && !success) {
            try {
                generateAndInsertChallenge(language, level, numQuestions, adminId, challengeDao, questionDao, answerDao, userChallengeDao, context);
                success = true;
            } catch (Exception e) {
                attempts++;
                if (attempts < maxRetries) {
                    System.out.println("Retrying " + language + " challenge generation... (" + attempts + "/" + maxRetries + ")");
                } else {
                    System.out.println("Failed to generate " + language + " challenge after " + maxRetries + " attempts.");
                }
            }
        }
    }

    private static void generateAndInsertChallenge(String language, String level, int numQuestions, int adminId,
                                                   ChallengeDAO challengeDao, QuestionDAO questionDao, AnswerDAO answerDao,
                                                   UserChallengeDAO userChallengeDao, Context context) throws Exception {
        OpenAIRepository openAIRepository = new OpenAIRepository();
        openAIRepository.generateChallenges(language, level, numQuestions, context);
    }
}
