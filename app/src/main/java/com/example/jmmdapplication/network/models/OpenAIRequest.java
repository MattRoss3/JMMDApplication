package com.example.jmmdapplication.network.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * OpenAIRequest represents the structure of the request sent to the OpenAI API.
 */
public class OpenAIRequest {

    @SerializedName("model")
    private String model;

    @SerializedName("messages")
    private List<Message> messages;

    @SerializedName("functions")
    private List<Function> functions;

    @SerializedName("function_call")
    private String functionCall;

    @SerializedName("max_tokens")
    private int maxTokens;

    public OpenAIRequest(String model, List<Message> messages, List<Function> functions, String functionCall, int maxTokens) {
        this.model = model;
        this.messages = messages;
        this.functions = functions;
        this.functionCall = functionCall;
        this.maxTokens = maxTokens;
    }

    public static class Message {
        @SerializedName("role")
        private String role;

        @SerializedName("content")
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }

    public static class Function {
        @SerializedName("name")
        private String name;

        @SerializedName("description")
        private String description;

        @SerializedName("parameters")
        private Parameters parameters;

        public Function(String name, String description, Parameters parameters) {
            this.name = name;
            this.description = description;
            this.parameters = parameters;
        }

        public static class Parameters {
            @SerializedName("type")
            private String type;

            @SerializedName("properties")
            private Properties properties;

            public Parameters(String type, Properties properties) {
                this.type = type;
                this.properties = properties;
            }

            public static class Properties {
                @SerializedName("language")
                private Language language;

                @SerializedName("level")
                private Level level;

                @SerializedName("num_questions")
                private NumQuestions numQuestions;

                @SerializedName("title")
                private Title title;

                @SerializedName("description")
                private Description description;

                @SerializedName("questions")
                private Questions questions;

                public Properties(Language language, Level level, NumQuestions numQuestions, Title title, Description description, Questions questions) {
                    this.language = language;
                    this.level = level;
                    this.numQuestions = numQuestions;
                    this.title = title;
                    this.description = description;
                    this.questions = questions;
                }

                public static class Language {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("description")
                    private String description;

                    public Language(String type, String description) {
                        this.type = type;
                        this.description = description;
                    }
                }

                public static class Level {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("description")
                    private String description;

                    public Level(String type, String description) {
                        this.type = type;
                        this.description = description;
                    }
                }

                public static class NumQuestions {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("description")
                    private String description;

                    public NumQuestions(String type, String description) {
                        this.type = type;
                        this.description = description;
                    }
                }

                public static class Title {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("description")
                    private String description;

                    public Title(String type, String description) {
                        this.type = type;
                        this.description = description;
                    }
                }

                public static class Description {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("description")
                    private String description;

                    public Description(String type, String description) {
                        this.type = type;
                        this.description = description;
                    }
                }

                public static class Questions {
                    @SerializedName("type")
                    private String type;

                    @SerializedName("items")
                    private QuestionItems items;

                    public Questions(String type, QuestionItems items) {
                        this.type = type;
                        this.items = items;
                    }

                    public static class QuestionItems {
                        @SerializedName("type")
                        private String type;

                        @SerializedName("properties")
                        private QuestionProperties properties;

                        public QuestionItems(String type, QuestionProperties properties) {
                            this.type = type;
                            this.properties = properties;
                        }

                        public static class QuestionProperties {
                            @SerializedName("question")
                            private QuestionText question;

                            @SerializedName("answers")
                            private Answers answers;

                            public QuestionProperties(QuestionText question, Answers answers) {
                                this.question = question;
                                this.answers = answers;
                            }

                            public static class QuestionText {
                                @SerializedName("type")
                                private String type;

                                @SerializedName("description")
                                private String description;

                                public QuestionText(String type, String description) {
                                    this.type = type;
                                    this.description = description;
                                }
                            }

                            public static class Answers {
                                @SerializedName("type")
                                private String type;

                                @SerializedName("items")
                                private AnswerItems items;

                                public Answers(String type, AnswerItems items) {
                                    this.type = type;
                                    this.items = items;
                                }

                                public static class AnswerItems {
                                    @SerializedName("type")
                                    private String type;

                                    @SerializedName("properties")
                                    private AnswerProperties properties;

                                    public AnswerItems(String type, AnswerProperties properties) {
                                        this.type = type;
                                        this.properties = properties;
                                    }

                                    public static class AnswerProperties {
                                        @SerializedName("text")
                                        private AnswerText text;

                                        @SerializedName("isCorrect")
                                        private IsCorrect isCorrect;

                                        public AnswerProperties(AnswerText text, IsCorrect isCorrect) {
                                            this.text = text;
                                            this.isCorrect = isCorrect;
                                        }

                                        public static class AnswerText {
                                            @SerializedName("type")
                                            private String type;

                                            @SerializedName("description")
                                            private String description;

                                            public AnswerText(String type, String description) {
                                                this.type = type;
                                                this.description = description;
                                            }
                                        }

                                        public static class IsCorrect {
                                            @SerializedName("type")
                                            private String type;

                                            @SerializedName("description")
                                            private String description;

                                            public IsCorrect(String type, String description) {
                                                this.type = type;
                                                this.description = description;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
