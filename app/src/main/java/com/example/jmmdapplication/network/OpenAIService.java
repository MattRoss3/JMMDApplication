package com.example.jmmdapplication.network;

import com.example.jmmdapplication.network.models.OpenAIRequest;
import com.example.jmmdapplication.network.models.OpenAIResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * OpenAIService defines the API endpoints for OpenAI.
 */
public interface OpenAIService {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    Call<OpenAIResponse> generateChallenges(
            @Header("Authorization") String authorization,
            @Body OpenAIRequest request
    );
}
