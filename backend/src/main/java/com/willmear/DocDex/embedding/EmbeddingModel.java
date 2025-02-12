package com.willmear.DocDex.embedding;

import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;

public class EmbeddingModel {

    @Bean
    public OpenAiEmbeddingModel embeddingModel() {
        // Can be any other EmbeddingModel implementation.
        return new OpenAiEmbeddingModel(new OpenAiApi(System.getenv("SPRING_AI_OPENAI_API_KEY")));
    }

}
