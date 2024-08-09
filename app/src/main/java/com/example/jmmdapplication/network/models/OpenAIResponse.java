package com.example.jmmdapplication.network.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class OpenAIResponse {

    @SerializedName("choices")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        @SerializedName("message")
        private Message message;

        public Message getMessage() {
            return message;
        }
    }

    public static class Message {
        @SerializedName("function_call")
        private FunctionCall functionCall;

        public FunctionCall getFunctionCall() {
            return functionCall;
        }
    }

    public static class FunctionCall {
        @SerializedName("name")
        private String name;

        @SerializedName("arguments")
        private String arguments;  // Store arguments as a string

        public String getName() {
            return name;
        }

        public String getArguments() {
            return arguments;
        }
    }
}
