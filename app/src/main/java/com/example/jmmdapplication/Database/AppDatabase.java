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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({LocalDataTypeConverter.class})
@Database(entities = {User.class, Challenge.class, UserChallenge.class, Progress.class, Question.class, Answer.class}, version = 12, exportSchema = false)
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
                            .addCallback(addDefaultValues)
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

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                AppDatabase database = getDatabase(null);
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

                // Insert Spanish challenge
                Challenge spanishChallenge = new Challenge("Spanish Basics", "Learn basic Spanish words.", "Spanish", true);
                challengeDao.insertChallenge(spanishChallenge);

                // Get the inserted Spanish challenge
                Challenge insertedSpanishChallenge = challengeDao.getChallengeByNameSync("Spanish Basics");
                if (insertedSpanishChallenge == null) return;
                int spanishChallengeId = insertedSpanishChallenge.getChallengeId();
                userChallengeDao.insertUserChallenge(new UserChallenge(adminId, spanishChallengeId));

                // Insert Spanish questions and answers
                insertSpanishQuestionsAndAnswers(questionDao, answerDao, spanishChallengeId);

                // Insert French challenge
                Challenge frenchChallenge = new Challenge("French Basics", "Learn basic French words.", "French", true);
                challengeDao.insertChallenge(frenchChallenge);

                // Get the inserted French challenge
                Challenge insertedFrenchChallenge = challengeDao.getChallengeByNameSync("French Basics");
                if (insertedFrenchChallenge == null) return;
                int frenchChallengeId = insertedFrenchChallenge.getChallengeId();
                userChallengeDao.insertUserChallenge(new UserChallenge(adminId, frenchChallengeId));

                // Insert French questions and answers
                insertFrenchQuestionsAndAnswers(questionDao, answerDao, frenchChallengeId);
            });
        }

        private void insertSpanishQuestionsAndAnswers(QuestionDAO questionDao, AnswerDAO answerDao, int challengeId) {
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for never?", "Spanish",
                    new String[]{"nunca", "sí", "no", "tengo"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for gonna?", "Spanish",
                    new String[]{"ir", "llamo", "nombre", "soy"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for give?", "Spanish",
                    new String[]{"dar", "de", "qué", "haces"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for you?", "Spanish",
                    new String[]{"tú", "va", "te", "estás"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for up?", "Spanish",
                    new String[]{"arriba", "tal", "bien", "siempre"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the Spanish word for let?", "Spanish",
                    new String[]{"dejar", "por", "favor", "gracias"}, new boolean[]{true, false, false, false});
        }

        private void insertFrenchQuestionsAndAnswers(QuestionDAO questionDao, AnswerDAO answerDao, int challengeId) {
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for down?", "French",
                    new String[]{"vers le bas", "Ennui", "Faux", "Escargot"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for run?", "French",
                    new String[]{"courir", "Noir", "Jour", "Nouveau"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for around?", "French",
                    new String[]{"autour", "Fromage", "comme", "je"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for and?", "French",
                    new String[]{"et", "était", "que", "avec"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for hurt?", "French",
                    new String[]{"blesser", "avoir", "chaud", "mais"}, new boolean[]{true, false, false, false});
            insertQuestionWithAnswers(questionDao, answerDao, challengeId, "What is the French word for goodbye?", "French",
                    new String[]{"au revoir", "est", "de", "dans"}, new boolean[]{true, false, false, false});
        }

        private void insertQuestionWithAnswers(QuestionDAO questionDao, AnswerDAO answerDao, int challengeId, String questionText, String language,
                                               String[] answers, boolean[] correctAnswers) {
            Question question = new Question(challengeId, questionText, language);
            questionDao.insertQuestion(question);

            // Get the inserted question
            Question insertedQuestion = questionDao.getQuestionByTextSync(questionText);
            if (insertedQuestion == null) return;
            int questionId = insertedQuestion.getQuestionId();

            for (int i = 0; i < answers.length; i++) {
                answerDao.insertAnswer(new Answer(questionId, answers[i], correctAnswers[i]));
            }
        }
    };
}
