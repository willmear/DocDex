package com.willmear.DocDex.service;

import com.willmear.DocDex.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmbeddingService {

    @Autowired
    VectorStore vectorStore;

    private final PdfService pdfService;

    public void addDocuments() throws IOException {

        List<Document> documentsList = pdfService.readPDF();

//        vectorStore.add(new TokenTextSplitter(300, 300, 5, 1000, true).split(documentsList));

        List<Document> results = this.vectorStore.similaritySearch(SearchRequest.builder().query("autoconfiguration of postgresql").topK(5).build());

        for (Document doc : results) {
            System.out.println(doc.getText());
        }


    }

}
