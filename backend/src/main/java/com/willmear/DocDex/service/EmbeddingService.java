package com.willmear.DocDex.service;

import com.willmear.DocDex.embedding.EmbeddingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmbeddingService {

    @Autowired
    VectorStore vectorStore;

    private final PdfService pdfService;

    public void addDocuments() throws IOException {

        vectorStore.add(pdfService.readPDF());

    }

}
