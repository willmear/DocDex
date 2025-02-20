package com.willmear.DocDex.controller;

import com.willmear.DocDex.entity.Conversation;
import com.willmear.DocDex.entity.dto.QuestionDto;
import com.willmear.DocDex.service.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/conversation/create")
    public ResponseEntity<Conversation> createConversation(@RequestBody QuestionDto questionDto) {
        return conversationService.createConversation(questionDto);
    }

    @GetMapping("/conversation/{id}")
    public ResponseEntity<Conversation> getConversation(@PathVariable Long id) {
        return conversationService.getConversation(id);
    }

    @GetMapping("/conversation/all")
    public ResponseEntity<List<Conversation>> getAllConversation() {
        return conversationService.getAllConversation();
    }

}
