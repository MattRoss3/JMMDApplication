package com.example.jmmdapplication.network;

import android.content.Context;
import android.util.Log;

import com.example.jmmdapplication.BuildConfig;
import com.example.jmmdapplication.Database.ChallengeInserter;
import com.example.jmmdapplication.network.models.OpenAIRequest;
import com.example.jmmdapplication.network.models.OpenAIResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * OpenAIRepository handles communication with the OpenAI API.
 */
public class OpenAIRepository {
    private final OpenAIService openAIService;
    private static final String TAG = "OpenAIRepository";

    /**
     * Constructor for OpenAIRepository.
     */
    public OpenAIRepository() {
        Retrofit retrofit = RetrofitClient.getClient();
        openAIService = retrofit.create(OpenAIService.class);
    }

    /**
     * Generates a language challenge and inserts it into the database.
     *
     * @param language     The language of the challenge
     * @param level        The difficulty level of the challenge
     * @param numQuestions The number of questions in the challenge
     * @param context      Application context
     */
    public void generateChallenges(String language, String level, int numQuestions, Context context) {
        // Construct the OpenAI request with proper message and function definitions
        OpenAIRequest request = new OpenAIRequest(
                "gpt-4o-2024-08-06",
                Arrays.asList(
                        new OpenAIRequest.Message("system", "You are an expert at creating educational content, specializing in structured data outputs."),
                        new OpenAIRequest.Message("user",
                                "{\n" +
                                        "  \"Title\": \"Basic Spanish Verbs\",\n" +
                                        "  \"Description\": \"Key action words\",\n" +
                                        "  \"Questions\": [\n" +
                                        "    {\n" +
                                        "      \"Question Text\": \"What is the Spanish word for 'to eat'?\",\n" +
                                        "      \"Answers\": [\n" +
                                        "        { \"Text\": \"comer\", \"IsCorrect\": true },\n" +
                                        "        { \"Text\": \"beber\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"correr\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"escribir\", \"IsCorrect\": false }\n" +
                                        "      ]\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "      \"Question Text\": \"What is the Spanish word for 'to drink'?\",\n" +
                                        "      \"Answers\": [\n" +
                                        "        { \"Text\": \"beber\", \"IsCorrect\": true },\n" +
                                        "        { \"Text\": \"comer\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"leer\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"correr\", \"IsCorrect\": false }\n" +
                                        "      ]\n" +
                                        "    },\n" +
                                        "    {\n" +
                                        "      \"Question Text\": \"What is the Spanish word for 'to run'?\",\n" +
                                        "      \"Answers\": [\n" +
                                        "        { \"Text\": \"correr\", \"IsCorrect\": true },\n" +
                                        "        { \"Text\": \"comer\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"beber\", \"IsCorrect\": false },\n" +
                                        "        { \"Text\": \"escribir\", \"IsCorrect\": false }\n" +
                                        "      ]\n" +
                                        "    }\n" +
                                        "  ]\n" +
                                        "}\n" +
                                        "\n" +
                                        "Please generate a structured language learning challenge in JSON format. The challenge is for a " + language + " learner at the " + level + " level. Ensure the output adheres to the following requirements:\n" +
                                        "\n" +
                                        "1. **Title**: A short, descriptive string that summarizes the challenge. This field must be non-empty.\n" +
                                        "2. **Description**: A concise string of 4-6 words that describes the challenge's focus. This field must be non-empty.\n" +
                                        "3. **Questions**: An array of " + numQuestions + " objects. Ensure this array is never null and always contains exactly " + numQuestions + " question objects. Each question object should include:\n" +
                                        "   - **Question Text**: A non-empty string containing the question. If this field is empty, provide a default question.\n" +
                                        "   - **Answers**: An array of four objects. Ensure this array is never null and always contains exactly four answer objects. Each answer object should include:\n" +
                                        "       - **Text**: A non-empty string representing a possible answer. If this field is empty, provide a default answer.\n" +
                                        "       - **IsCorrect**: A boolean indicating whether this answer is correct. Ensure that exactly one of the answers has `IsCorrect` set to `true` and the others set to `false`.\n\n" +
                                        "Please make sure that the output is formatted as a valid JSON object and that all fields are populated with non-null, non-empty values. If any value is missing, replace it with a meaningful default."
                        )
                ),
                Arrays.asList(new OpenAIRequest.Function(
                        "generate_language_challenge",
                        "Generates a language learning challenge with a title, short description, and a set of questions, each with four possible answers.",
                        new OpenAIRequest.Function.Parameters("object", new OpenAIRequest.Function.Parameters.Properties(
                                new OpenAIRequest.Function.Parameters.Properties.Language("string", "The language for the challenge"),
                                new OpenAIRequest.Function.Parameters.Properties.Level("string", "The difficulty level of the challenge"),
                                new OpenAIRequest.Function.Parameters.Properties.NumQuestions("integer", "The number of questions"),
                                new OpenAIRequest.Function.Parameters.Properties.Title("string", "The title of the challenge"),
                                new OpenAIRequest.Function.Parameters.Properties.Description("string", "A short (4-6 words) description of the challenge"),
                                new OpenAIRequest.Function.Parameters.Properties.Questions("array", new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems(
                                        "object", new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties(
                                        new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.QuestionText(
                                                "string", "The text of the question"),
                                        new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.Answers(
                                                "array", new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.Answers.AnswerItems(
                                                "object", new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.Answers.AnswerItems.AnswerProperties(
                                                new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.Answers.AnswerItems.AnswerProperties.AnswerText(
                                                        "string", "The text of the answer"),
                                                new OpenAIRequest.Function.Parameters.Properties.Questions.QuestionItems.QuestionProperties.Answers.AnswerItems.AnswerProperties.IsCorrect(
                                                        "boolean", "Whether the answer is correct")
                                        )
                                        )
                                        )
                                )
                                ))
                        ))
                )),
                "auto",
                7000  // Ensure this aligns with the model's token limits
        );

        // Set the API key
        String apiKey = "Bearer " + BuildConfig.OPENAI_API_KEY;

        // Make the Retrofit call to generate challenges
        openAIService.generateChallenges(apiKey, request).enqueue(new Callback<OpenAIResponse>() {
            @Override
            public void onResponse(Call<OpenAIResponse> call, Response<OpenAIResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (OpenAIResponse.Choice choice : response.body().getChoices()) {
                        OpenAIResponse.FunctionCall functionCall = choice.getMessage().getFunctionCall();
                        if (functionCall != null && "generate_language_challenge".equals(functionCall.getName())) {
                            try {
                                String rawArguments = functionCall.getArguments();
                                Log.d(TAG, "Raw arguments: " + rawArguments);

                                JSONObject arguments = new JSONObject(rawArguments);

                                String title = arguments.optString("title", "No Title");
                                String description = arguments.optString("description", "No Description");
                                String language = arguments.optString("language", "No Language");
                                JSONArray questionsArray = arguments.optJSONArray("questions");

                                Log.d(TAG, "Challenge Title: " + title);
                                Log.d(TAG, "Challenge Description: " + description);
                                Log.d(TAG, "Language: " + language);
                                Log.d(TAG, "Questions Array: " + (questionsArray != null ? questionsArray.toString() : "No Questions"));

                                if (questionsArray != null) {
                                    ChallengeInserter inserter = new ChallengeInserter(context);
                                    inserter.insertChallenge(title, description, language, questionsArray);
                                } else {
                                    Log.e(TAG, "Questions array is null or empty.");
                                }

                            } catch (JSONException e) {
                                Log.e(TAG, "Failed to parse arguments JSON: " + e.getMessage());
                            }
                        } else {
                            Log.e(TAG, "Function call was null or function name didn't match.");
                        }
                    }
                } else {
                    Log.e(TAG, "Response was not successful. Code: " + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e(TAG, "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to parse error body: " + e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OpenAIResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
            }
        });
    }
}
