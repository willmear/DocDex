package com.willmear.DocDex.repository;

import com.willmear.DocDex.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
