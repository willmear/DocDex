package com.willmear.DocDex.repository;

import com.willmear.DocDex.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
