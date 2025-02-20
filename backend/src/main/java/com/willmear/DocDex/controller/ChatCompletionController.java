package com.willmear.DocDex.controller;

import com.willmear.DocDex.entity.Chat;
import com.willmear.DocDex.entity.dto.CompletionDto;
import com.willmear.DocDex.entity.dto.QuestionDto;
import com.willmear.DocDex.service.ChatCompletionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatCompletionController {

    private final ChatCompletionService completionService;

    @MessageMapping("/question")
    @SendTo("/topic/questions")
    public Chat askQuestion(@Payload QuestionDto question) {
        System.out.println(question);

        return completionService.chatCompletion(question);
    }


}
