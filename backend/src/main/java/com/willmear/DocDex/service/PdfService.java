package com.willmear.DocDex.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfService {


    public static List<Document> readPDF() throws IOException {

            File file = new File("C:/Users/willi/Desktop/DocDex/spring-boot-reference.pdf");
            PDDocument document = Loader.loadPDF(file);
            Splitter splitter = new Splitter();
            splitter.setSplitAtPage(1);
            List<PDDocument> documents = splitter.split(document);
            PDFTextStripper stripper = new PDFTextStripper();

            List<Document> documentList = new ArrayList<>();

            Integer docs = 1;
            for (PDDocument document1 : documents) {
                Map<String, Object> metadata = new HashMap<>();
                metadata.put("page", docs);
                Document document2 = new Document(stripper.getText(document1), metadata);
                documentList.add(document2);
                docs++;
            }


        return documentList;
    }

}
