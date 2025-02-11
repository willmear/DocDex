package com.willmear.DocDex.service;

import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            for (PDDocument document1 : documents) {
                Document document2 = new Document(stripper.getText(document1));
                documentList.add(document2);
            }


        return documentList;
    }

}
