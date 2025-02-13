package com.willmear.DocDex.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chat-completion")
@RequiredArgsConstructor
public class ChatCompletionController {

    @GetMapping("/question")
    public ResponseEntity<Object> askQuestion() {
        return null;
    }

}
