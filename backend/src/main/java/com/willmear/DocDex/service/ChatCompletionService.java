package com.willmear.DocDex.service;

import com.willmear.DocDex.entity.dto.CompletionDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatCompletionService {

    @Autowired
    VectorStore vectorStore;

    private final ChatModel chatModel;

    @Autowired
    private ChatClient.Builder chatClientBuilder;


    public ResponseEntity<CompletionDto> chatCompletion(String question) {

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

        List<Document> documents = retriever.retrieve(new Query(question));



        ChatResponse response = ChatClient.builder(chatModel)
                        .build().prompt()
                        .advisors(retrievalAugmentationAdvisor)
                        .user(question)
                .system("You are a Spring Boot expert. The provided context is from the Spring documentation.")
                        .call()
                    .chatResponse();


        assert response != null;
        String textResponse = response.getResult().getOutput().getContent();
        System.out.println(textResponse);

        List<Integer> pages = new ArrayList<>();

        for (Document document: documents) {
            pages.add((Integer) document.getMetadata().get("page"));
        }

        CompletionDto completionDto = CompletionDto.builder()
                .text(textResponse)
                .pages(pages)
                .build();

        return ResponseEntity.ok(completionDto);
    }

}
