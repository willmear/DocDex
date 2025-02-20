package com.willmear.DocDex.entity;

import com.willmear.DocDex.enums.SenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String text;
    private LocalDateTime sentAt = LocalDateTime.now();
    @Enumerated
    private SenderType senderType;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Integer> pages;

}
