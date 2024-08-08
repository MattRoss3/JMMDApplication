package com.example.jmmdapplication.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jmmdapplication.Database.DAO.AnswerDAO;
import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.DAO.ProgressDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.typeConverters.LocalDataTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({LocalDataTypeConverter.class})
@Database(entities = {User.class, Challenge.class, Progress.class, Question.class, Answer.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

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

    private static final RoomDatabase.Callback addDefaultValues =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        UserDAO userDao = INSTANCE.userDAO();

                        ChallengeDAO challengeDao = INSTANCE.challengeDAO();
                        QuestionDAO questionDao = INSTANCE.questionDAO();
                        AnswerDAO answerDao = INSTANCE.answerDAO();

                        User admin = new User("admin", "password", true);
                        userDao.insertUser(admin);

                        int adminId = userDao.getUserByUsername("admin").getUserId();

                        // Challenge 1: Spanish
                        Challenge spanishChallenge = new Challenge("Spanish Basics", "Learn basic Spanish words.", "Spanish", true);
                        spanishChallenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(spanishChallenge);
                        int spanishChallengeId = challengeDao.getChallengesByUserId(adminId).get(0).getChallengeId();

                        // Spanish Questions and Answers
                        Question spanishQuestion1 = new Question(spanishChallengeId, "What is the Spanish word for never?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion1);
                        int spanishQuestion1Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(0).getQuestionId();
                        answerDao.insertAnswer(new Answer(spanishQuestion1Id, "nunca", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion1Id, "sí", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion1Id, "no", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion1Id, "tengo", false));

                        // Spanish Question 2
                        Question spanishQuestion2 = new Question(spanishChallengeId, "What is the Spanish word for gonna?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion2);
                        int spanishQuestion2Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanishQuestion2Id, "ir", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion2Id, "llamo", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion2Id, "nombre", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion2Id, "soy", false));

                        // Spanish Question 3
                        Question spanishQuestion3 = new Question(spanishChallengeId, "What is the Spanish word for give?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion3);
                        int spanishQuestion3Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanishQuestion3Id, "dar", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion3Id, "de", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion3Id, "qué", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion3Id, "haces", false));

                        // Spanish Question 4
                        Question spanishQuestion4 = new Question(spanishChallengeId, "What is the Spanish word for you?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion4);
                        int spanishQuestion4Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanishQuestion4Id, "tú", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion4Id, "va", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion4Id, "te", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion4Id, "estás", false));

                        // Spanish Question 5
                        Question spanishQuestion5 = new Question(spanishChallengeId, "What is the Spanish word for up?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion5);
                        int spanishQuestion5Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanishQuestion5Id, "arriba", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion5Id, "tal", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion5Id, "bien", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion5Id, "siempre", false));

                        // Spanish Question 6
                        Question spanishQuestion6 = new Question(spanishChallengeId, "What is the Spanish word for let?", "Spanish");
                        questionDao.insertQuestion(spanishQuestion6);
                        int spanishQuestion6Id = questionDao.getQuestionsByChallengeId(spanishChallengeId).get(5).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanishQuestion6Id, "dejar", true));
                        answerDao.insertAnswer(new Answer(spanishQuestion6Id, "por", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion6Id, "favor", false));
                        answerDao.insertAnswer(new Answer(spanishQuestion6Id, "gracias", false));

                        // Challenge 2: French
                        Challenge frenchChallenge = new Challenge("French Basics", "Learn basic French words.", "French", true);
                        frenchChallenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(frenchChallenge);
                        int frenchChallengeId = challengeDao.getChallengesByUserId(adminId).get(1).getChallengeId();


                        // French Questions and Answers
                        Question frenchQuestion1 = new Question(frenchChallengeId, "What is the French word for down?", "French");
                        questionDao.insertQuestion(frenchQuestion1);
                        int frenchQuestion1Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(0).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion1Id, "vers le bas", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion1Id, "Ennui", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion1Id, "Faux", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion1Id, "Escargot", false));

                        Question frenchQuestion2 = new Question(frenchChallengeId, "What is the French word for run?", "French");
                        questionDao.insertQuestion(frenchQuestion2);
                        int frenchQuestion2Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion2Id, "courir", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion2Id, "Noir", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion2Id, "Jour", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion2Id, "Nouveau", false));

                        Question frenchQuestion3 = new Question(frenchChallengeId, "What is the French word for around?", "French");
                        questionDao.insertQuestion(frenchQuestion3);
                        int frenchQuestion3Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion3Id, "autour", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion3Id, "Fromage", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion3Id, "comme", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion3Id, "je", false));

                        Question frenchQuestion4 = new Question(frenchChallengeId, "What is the French word for and?", "French");
                        questionDao.insertQuestion(frenchQuestion4);
                        int frenchQuestion4Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion4Id, "et", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion4Id, "était", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion4Id, "que", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion4Id, "avec", false));

                        Question frenchQuestion5 = new Question(frenchChallengeId, "What is the French word for hurt?", "French");
                        questionDao.insertQuestion(frenchQuestion5);
                        int frenchQuestion5Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "blesser", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "avoir", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "chaud", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "mais", false));

                        Question frenchQuestion6 = new Question(frenchChallengeId, "What is the French word for goodbye?", "French");
                        questionDao.insertQuestion(frenchQuestion6);
                        int frenchQuestion6Id = questionDao.getQuestionsByChallengeId(frenchChallengeId).get(5).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion6Id, "au revoir", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion6Id, "est", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion6Id, "de", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion6Id, "dans", false));

                        Challenge spanish1Challenge = new Challenge("Spanish Basics 2", "Learn basic Spanish words.", "Spanish", true);
                        spanishChallenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(spanish1Challenge);
                        int spanish1ChallengeId = challengeDao.getChallengesByUserId(adminId).get(2).getChallengeId();


// Spanish Questions and Answers
                        Question spanish1Question1 = new Question(spanish1ChallengeId, "What is the Spanish word for hello?", "Spanish");
                        questionDao.insertQuestion(spanish1Question1);
                        int spanish1Question1Id = questionDao.getQuestionsByChallengeId(spanish1ChallengeId).get(0).getQuestionId();
                        answerDao.insertAnswer(new Answer(spanish1Question1Id, "hola", true));
                        answerDao.insertAnswer(new Answer(spanish1Question1Id, "buenos", false));
                        answerDao.insertAnswer(new Answer(spanish1Question1Id, "tardes", false));
                        answerDao.insertAnswer(new Answer(spanish1Question1Id, "amigo", false));
// Spanish Question 2
                        Question spanish1Question2 = new Question(spanish1ChallengeId, "What is the Spanish word for goodbye?", "Spanish");
                        questionDao.insertQuestion(spanish1Question2);
                        int spanish1Question2Id = questionDao.getQuestionsByChallengeId(spanish1ChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish1Question2Id, "adios", true));
                        answerDao.insertAnswer(new Answer(spanish1Question2Id, "mi", false));
                        answerDao.insertAnswer(new Answer(spanish1Question2Id, "nombre", false));
                        answerDao.insertAnswer(new Answer(spanish1Question2Id, "es", false));


// Spanish Question 3
                        Question spanish1Question3 = new Question(spanish1ChallengeId, "What is the Spanish word for library?", "Spanish");
                        questionDao.insertQuestion(spanish1Question3);
                        int spanish1Question3Id = questionDao.getQuestionsByChallengeId(spanish1ChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish1Question3Id, "donde", false));
                        answerDao.insertAnswer(new Answer(spanish1Question3Id, "esta", false));
                        answerDao.insertAnswer(new Answer(spanish1Question3Id, "la", false));
                        answerDao.insertAnswer(new Answer(spanish1Question3Id, "biblioteca", true));


// Spanish Question 4
                        Question spanish1Question4 = new Question(spanish1ChallengeId, "What is the Spanish word for eat?", "Spanish");
                        questionDao.insertQuestion(spanish1Question4);
                        int spanish1Question4Id = questionDao.getQuestionsByChallengeId(spanish1ChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish1Question4Id, "comer", true));
                        answerDao.insertAnswer(new Answer(spanish1Question4Id, "caliente", false));
                        answerDao.insertAnswer(new Answer(spanish1Question4Id, "mucho", false));
                        answerDao.insertAnswer(new Answer(spanish1Question4Id, "grande", false));


// Spanish Question 5
                        Question spanish1Question5 = new Question(spanish1ChallengeId, "What is the Spanish word for play?", "Spanish");
                        questionDao.insertQuestion(spanish1Question5);
                        int spanish1Question5Id = questionDao.getQuestionsByChallengeId(spanish1ChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish1Question5Id, "juego", true));
                        answerDao.insertAnswer(new Answer(spanish1Question5Id, "nombre", false));
                        answerDao.insertAnswer(new Answer(spanish1Question5Id, "bien", false));
                        answerDao.insertAnswer(new Answer(spanish1Question5Id, "siempre", false));

                        Challenge spanish2Challenge = new Challenge("Spanish Basics 3", "Learn basic Spanish words.", "Spanish", true);
                        spanishChallenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(spanish2Challenge);
                        int spanish2ChallengeId = challengeDao.getChallengesByUserId(adminId).get(3).getChallengeId();


// Spanish Questions and Answers
                        Question spanish2Question1 = new Question(spanish2ChallengeId, "What is the Spanish word for goodbye?", "Spanish");
                        questionDao.insertQuestion(spanish2Question1);
                        int spanish2Question1Id = questionDao.getQuestionsByChallengeId(spanish2ChallengeId).get(0).getQuestionId();
                        answerDao.insertAnswer(new Answer(spanish2Question1Id, "adios", true));
                        answerDao.insertAnswer(new Answer(spanish2Question1Id, "domingo", false));
                        answerDao.insertAnswer(new Answer(spanish2Question1Id, "taco", false));
                        answerDao.insertAnswer(new Answer(spanish2Question1Id, "amigo", false));
// Spanish Question 2
                        Question spanish2Question2 = new Question(spanish2ChallengeId, "What is the Spanish word for Monday?", "Spanish");
                        questionDao.insertQuestion(spanish2Question2);
                        int spanish2Question2Id = questionDao.getQuestionsByChallengeId(spanish2ChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish2Question2Id, "martes", true));
                        answerDao.insertAnswer(new Answer(spanish2Question2Id, "burrito", false));
                        answerDao.insertAnswer(new Answer(spanish2Question2Id, "quesadilla", false));
                        answerDao.insertAnswer(new Answer(spanish2Question2Id, "birria", false));


// Spanish Question 3
                        Question spanish2Question3 = new Question(spanish2ChallengeId, "What is the Spanish word for butter?", "Spanish");
                        questionDao.insertQuestion(spanish2Question3);
                        int spanish2Question3Id = questionDao.getQuestionsByChallengeId(spanish2ChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish2Question3Id, "manteca", true));
                        answerDao.insertAnswer(new Answer(spanish2Question3Id, "luna", false));
                        answerDao.insertAnswer(new Answer(spanish2Question3Id, "sol", false));
                        answerDao.insertAnswer(new Answer(spanish2Question3Id, "torta", false));


// Spanish Question 4
                        Question spanish2Question4 = new Question(spanish2ChallengeId, "What is the Spanish word for love?", "Spanish");
                        questionDao.insertQuestion(spanish2Question4);
                        int spanish2Question4Id = questionDao.getQuestionsByChallengeId(spanish2ChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish2Question4Id, "amar", true));
                        answerDao.insertAnswer(new Answer(spanish2Question4Id, "luna", false));
                        answerDao.insertAnswer(new Answer(spanish2Question4Id, "mucho", false));
                        answerDao.insertAnswer(new Answer(spanish2Question4Id, "siempre", false));


// Spanish Question 5
                        Question spanish2Question5 = new Question(spanish2ChallengeId, "What is the Spanish word for rock?", "Spanish");
                        questionDao.insertQuestion(spanish2Question5);
                        int spanish2Question5Id = questionDao.getQuestionsByChallengeId(spanish2ChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(spanish2Question5Id, "roca", true));
                        answerDao.insertAnswer(new Answer(spanish2Question5Id, "manteca", false));
                        answerDao.insertAnswer(new Answer(spanish2Question5Id, "bien", false));
                        answerDao.insertAnswer(new Answer(spanish2Question5Id, "sol", false));




// Challenge 2: French
                        Challenge french1Challenge = new Challenge("French Basics 1", "Learn basic French words.", "French", true);
                        french1Challenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(french1Challenge);
                        int french1ChallengeId = challengeDao.getChallengesByUserId(adminId).get(4).getChallengeId();




// French Questions and Answers
                        Question french1Question1 = new Question(french1ChallengeId, "What is the French word for hello?", "French");
                        questionDao.insertQuestion(french1Question1);
                        int french1Question1Id = questionDao.getQuestionsByChallengeId(french1ChallengeId).get(0).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french1Question1Id, "bonjour", true));
                        answerDao.insertAnswer(new Answer(french1Question1Id, "oui", false));
                        answerDao.insertAnswer(new Answer(french1Question1Id, "merci", false));
                        answerDao.insertAnswer(new Answer(french1Question1Id, "Escargot", false));


                        Question french1Question2 = new Question(french1ChallengeId, "What is the French word for no?", "French");
                        questionDao.insertQuestion(french1Question2);
                        int french1Question2Id = questionDao.getQuestionsByChallengeId(french1ChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french1Question2Id, "non", true));
                        answerDao.insertAnswer(new Answer(french1Question2Id, "femme", false));
                        answerDao.insertAnswer(new Answer(french1Question2Id, "Jour", false));
                        answerDao.insertAnswer(new Answer(french1Question2Id, "amour", false));


                        Question french1Question3 = new Question(french1ChallengeId, "What is the French word for man", "French");
                        questionDao.insertQuestion(frenchQuestion3);
                        int french1Question3Id = questionDao.getQuestionsByChallengeId(french1ChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french1Question3Id, "homme", true));
                        answerDao.insertAnswer(new Answer(french1Question3Id, "bonsoir", false));
                        answerDao.insertAnswer(new Answer(french1Question3Id, "comme", false));
                        answerDao.insertAnswer(new Answer(french1Question3Id, "je", false));


                        Question french1Question4 = new Question(french1ChallengeId, "What is the French word for time?", "French");
                        questionDao.insertQuestion(french1Question4);
                        int french1Question4Id = questionDao.getQuestionsByChallengeId(french1ChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french1Question4Id, "temps", true));
                        answerDao.insertAnswer(new Answer(french1Question4Id, "bonne nuit", false));
                        answerDao.insertAnswer(new Answer(french1Question4Id, "de rien", false));
                        answerDao.insertAnswer(new Answer(french1Question4Id, "jour", false));


                        Question french1Question5 = new Question(french1ChallengeId, "What is the French word for day?", "French");
                        questionDao.insertQuestion(french1Question5);
                        int french1Question5Id = questionDao.getQuestionsByChallengeId(french1ChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french1Question5Id, "jour", true));
                        answerDao.insertAnswer(new Answer(french1Question5Id, "monde", false));
                                answerDao.insertAnswer(new Answer(french1Question5Id, "raison", false));
                        answerDao.insertAnswer(new Answer(french1Question5Id, "mais", false));




// Challenge 2: French
                        Challenge french2Challenge = new Challenge("French Basics 2", "Learn basic French words.", "French", true);
                        french2Challenge.setUserId(adminId); // Assigning to admin
                        challengeDao.insertChallenge(french2Challenge);
                        int french2ChallengeId = challengeDao.getChallengesByUserId(adminId).get(5).getChallengeId();




// French Questions and Answers
                        Question french2Question1 = new Question(french2ChallengeId, "What is the French word for world?", "French");
                        questionDao.insertQuestion(french2Question1);
                        int french2Question1Id = questionDao.getQuestionsByChallengeId(french2ChallengeId).get(0).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french2Question1Id, "monde", true));
                        answerDao.insertAnswer(new Answer(french2Question1Id, "monsieur", false));
                        answerDao.insertAnswer(new Answer(french2Question1Id, "belle", false));
                        answerDao.insertAnswer(new Answer(french2Question1Id, "Escargot", false));


                        Question french2Question2 = new Question(french2ChallengeId, "What is the French word for reason?", "French");
                        questionDao.insertQuestion(french2Question2);
                        int french2Question2Id = questionDao.getQuestionsByChallengeId(french2ChallengeId).get(1).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french2Question2Id, "raison", true));
                        answerDao.insertAnswer(new Answer(french2Question2Id, "beau", false));
                        answerDao.insertAnswer(new Answer(french2Question2Id, "chat", false));
                        answerDao.insertAnswer(new Answer(french2Question2Id, "chien", false));


                        Question french2Question3 = new Question(french2ChallengeId, "What is the French word for dog?", "French");
                        questionDao.insertQuestion(french2Question3);
                        int french2Question3Id = questionDao.getQuestionsByChallengeId(french2ChallengeId).get(2).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french2Question3Id, "chien", true));
                        answerDao.insertAnswer(new Answer(french2Question3Id, "chat", false));
                        answerDao.insertAnswer(new Answer(french2Question3Id, "fort", false));
                        answerDao.insertAnswer(new Answer(french2Question3Id, "je", false));


                        Question french2Question4 = new Question(frenchChallengeId, "What is the French word for excuse me?", "French");
                        questionDao.insertQuestion(french2Question4);
                        int french2Question4Id = questionDao.getQuestionsByChallengeId(french2ChallengeId).get(3).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(french2Question4Id, "excusez-moi", true));
                        answerDao.insertAnswer(new Answer(french2Question4Id, "de rien", false));
                        answerDao.insertAnswer(new Answer(french2Question4Id, "bonsoir", false));
                        answerDao.insertAnswer(new Answer(french2Question4Id, "avec", false));


                        Question french2Question5 = new Question(french2ChallengeId, "What is the French word for miss?", "French");
                        questionDao.insertQuestion(french2Question5);
                        int french2Question5Id = questionDao.getQuestionsByChallengeId(french2ChallengeId).get(4).getQuestionId(); // Correct way to retrieve questionId
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "mademoiselle", true));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "beau", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "belle", false));
                        answerDao.insertAnswer(new Answer(frenchQuestion5Id, "mais", false));
                    });
                }
            };
}
