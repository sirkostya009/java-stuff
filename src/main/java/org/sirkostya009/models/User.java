package org.sirkostya009.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public @Data class User {
    private String name, username, password;
}
