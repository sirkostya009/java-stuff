package org.sirkostya009.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
public @Data class Token {
    private String uuid;
    private LocalDateTime expiresAt;
}
