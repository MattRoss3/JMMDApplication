package com.example.jmmdapplication.Database;

import android.content.Context;
import android.util.Log;

import com.example.jmmdapplication.Database.DAO.AnswerDAO;
import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.entities.Answer;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ChallengeInserter handles inserting a challenge, its questions, and their respective answers into the database.
 */
public class ChallengeInserter {

    private final ChallengeDAO challengeDAO;
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;

    /**
     * Constructor for ChallengeInserter.
     *
     * @param context Application context
     */
    public ChallengeInserter(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.challengeDAO = db.challengeDAO();
        this.questionDAO = db.questionDAO();
        this.answerDAO = db.answerDAO();
    }

    /**
     * Inserts a challenge into the database.
     *
     * @param title          The title of the challenge
     * @param description    The description of the challenge
     * @param language       The language of the challenge
     * @param questionsArray JSON array of questions
     */
    public void insertChallenge(String title, String description, String language, JSONArray questionsArray) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            // Insert the Challenge
            Challenge challenge = new Challenge(title, description, language, true);
            challengeDAO.insertChallenge(challenge);

            // Retrieve the inserted Challenge to get its ID
            Challenge insertedChallenge = challengeDAO.getChallengeByNameSync(title);
            if (insertedChallenge == null) return;
            int challengeId = insertedChallenge.getChallengeId();

            // Insert Questions and Answers
            insertQuestionsAndAnswers(challengeId, questionsArray);
        });
    }

    /**
     * Inserts questions and answers into the database.
     *
     * @param challengeId    The ID of the challenge
     * @param questionsArray JSON array of questions
     */
    private void insertQuestionsAndAnswers(int challengeId, JSONArray questionsArray) {
        try {
            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionJson = questionsArray.getJSONObject(i);
                String questionText = questionJson.getString("question");
                JSONArray answersArray = questionJson.getJSONArray("answers");

                Question question = new Question(challengeId, questionText, "Spanish");
                questionDAO.insertQuestion(question);

                // Retrieve the inserted Question to get its ID
                Question insertedQuestion = questionDAO.getQuestionByTextSync(questionText);
                if (insertedQuestion == null) return;
                int questionId = insertedQuestion.getQuestionId();

                // Insert Answers
                for (int j = 0; j < answersArray.length(); j++) {
                    JSONObject answerJson = answersArray.getJSONObject(j);
                    String answerText = answerJson.getString("text");
                    boolean isCorrect = answerJson.getBoolean("isCorrect");

                    Answer answer = new Answer(questionId, answerText, isCorrect);
                    answerDAO.insertAnswer(answer);
                }
            }
        } catch (JSONException e) {
            Log.e("ChallengeInserter", "Error parsing JSON", e);
        }
    }
}
