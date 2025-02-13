package com.willmear.DocDex;

import com.willmear.DocDex.service.ChatCompletionService;
import com.willmear.DocDex.service.EmbeddingService;
import com.willmear.DocDex.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private PdfService pdfService;
    @Autowired
    private EmbeddingService embeddingService;
    @Autowired
    private ChatCompletionService chatCompletionService;

    @Override
    public void run(String... args) throws Exception {
//        embeddingService.addDocuments();
//        chatCompletionService.chatCompletion();
    }
}