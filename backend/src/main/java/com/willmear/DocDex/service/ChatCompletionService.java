package com.willmear.DocDex.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.postretrieval.ranking.DocumentRanker;
import org.springframework.ai.rag.postretrieval.selection.DocumentSelector;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatCompletionService {

    @Autowired
    VectorStore vectorStore;

    private final ChatModel chatModel;

    @Autowired
    private ChatClient.Builder chatClientBuilder;

    String userText = "What is a bean";


    public void chatCompletion() {

        DocumentRetriever retriever = VectorStoreDocumentRetriever.builder()
                .similarityThreshold(0.75)
                .topK(5)
                .vectorStore(vectorStore)
                .build();

        QueryTransformer queryTransformer = RewriteQueryTransformer.builder()
                .chatClientBuilder(chatClientBuilder.build().mutate())
                .build();

        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(queryTransformer)
                .documentRetriever(retriever)
                .build();


        List<Document> documents = retriever.retrieve(queryTransformer.transform(new Query(userText)));


//        ChatResponse response = ChatClient.builder(chatModel)
//                .build().prompt()
//                .advisors(new QuestionAnswerAdvisor(vectorStore))
//                .user(userText)
//                .call()
//                .chatResponse();

        ChatResponse response = ChatClient.builder(chatModel)
                        .build().prompt()
                        .advisors(retrievalAugmentationAdvisor)
                        .user(userText)
                .system("You are a Spring Boot expert. The provided context is from the Spring documentation that thee user has not provided.")
                        .call()
                    .chatResponse();

        System.out.println(response);

    }

}
