package org.sirkostya009.repos;

import org.sirkostya009.models.Token;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TokenRepository {
    private final static Set<Token> TOKENS = new HashSet<>();

    public Optional<Token> findByUUID(String uuid) {
        return TOKENS.stream()
                .filter(token -> token.getUuid().equals(uuid))
                .findFirst();
    }

    public Token save(Token token) {
        var isADuplicate = TOKENS.stream().anyMatch(token1 -> token1.getUuid().equals(token.getUuid()));

        if (isADuplicate)
            throw new RuntimeException("Token already exists");

        TOKENS.add(token);

        return token;
    }

    public Token delete(Token token) {
        TOKENS.remove(token);

        return token;
    }
}
