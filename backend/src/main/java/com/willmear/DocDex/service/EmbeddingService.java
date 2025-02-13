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

import javax.print.Doc;
import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class EmbeddingService {

    @Autowired
    VectorStore vectorStore;

    private final PdfService pdfService;

    public void addDocuments() throws IOException {

        List<Document> documentsList = pdfService.readPDF();


        vectorStore.add(new TokenTextSplitter(500, 500, 7, 2000, true).split(documentsList));

        List<Document> results = orderByPage(Objects.requireNonNull(this.vectorStore
                .similaritySearch(SearchRequest.builder().query("What is a Bean in spring boot").topK(5).build())));

        System.out.println(results);

    }

    private List<Document> orderByPage(List<Document> documentList) {

        List<Document> orderedDocuments = new ArrayList<>();
        for (int i = 1; i < documentList.size(); i++) {
            for (int j = 0; j < i; j++) {
                if ((Integer) documentList.get(j).getMetadata().get("page") > (Integer) documentList.get(i).getMetadata().get("page")) {
                    Document temp = documentList.get(i);
                    documentList.remove(i);
                    documentList.add(j, temp);
                }
            }
        }



        return documentList;
    }

}
