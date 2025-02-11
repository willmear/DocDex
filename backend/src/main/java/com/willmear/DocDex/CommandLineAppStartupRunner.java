package com.willmear.DocDex;

import com.willmear.DocDex.service.EmbeddingService;
import com.willmear.DocDex.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    private PdfService pdfService;
    @Autowired
    private EmbeddingService embeddingService;

    @Override
    public void run(String... args) throws Exception {
        embeddingService.addDocuments();
    }
}