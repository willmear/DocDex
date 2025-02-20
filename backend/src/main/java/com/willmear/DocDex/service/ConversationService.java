package com.willmear.DocDex.service;

import com.willmear.DocDex.entity.Chat;
import com.willmear.DocDex.entity.Conversation;
import com.willmear.DocDex.entity.dto.QuestionDto;
import com.willmear.DocDex.enums.SenderType;
import com.willmear.DocDex.repository.ChatRepository;
import com.willmear.DocDex.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatRepository chatRepository;
    private final ChatCompletionService completionService;

    public ResponseEntity<Conversation> createConversation(QuestionDto questionDto) {

        String text = questionDto.getText();


        List<Chat> message = new ArrayList<>();

        if (text.length() > 30) {
            text = text.substring(0,30);
        }

        Conversation conversation = Conversation.builder()
                .name(text)
                .messages(message)
                .build();

        Conversation savedConversation = conversationRepository.save(conversation);
        questionDto.setConversationId(savedConversation.getId());
        completionService.chatCompletion(questionDto);

        return ResponseEntity.ok(conversation);

    }

    public ResponseEntity<Conversation> getConversation(Long id) {
        Conversation conversation = conversationRepository.findById(id).orElse(null);

        return ResponseEntity.ok(conversation);
    }

    public ResponseEntity<List<Conversation>> getAllConversation() {
        List<Conversation> conversation = conversationRepository.findAll();

        return ResponseEntity.ok(conversation);
    }
}
