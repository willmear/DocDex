package com.willmear.DocDex.controller;

import com.willmear.DocDex.entity.dto.CompletionDto;
import com.willmear.DocDex.service.ChatCompletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chat-completion")
@RequiredArgsConstructor
public class ChatCompletionController {

    private final ChatCompletionService completionService;

    @GetMapping("/question/{question}")
    public ResponseEntity<CompletionDto> askQuestion(@PathVariable String question) {
        System.out.println(question);
        return completionService.chatCompletion(question);
    }

}
