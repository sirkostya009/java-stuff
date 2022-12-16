package org.sirkostya009.service;

import org.sirkostya009.models.Token;
import org.sirkostya009.repos.TokenRepository;
import org.sirkostya009.repos.UserRepository;
import org.sirkostya009.security.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthenticationService {

    private final PasswordEncoder encoder = new PasswordEncoder();
    private final UserRepository userRepository = new UserRepository();
    private final TokenRepository tokenRepository = new TokenRepository();

    private Token generateToken() {
        var uuid = UUID.randomUUID().toString();

        return tokenRepository.findByUUID(uuid).isPresent()
                ? generateToken()
                : tokenRepository.save(new Token(uuid, LocalDateTime.now().plusDays(7)));
    }

    public boolean tokenIsValid(String uuid) {
        var token = tokenRepository.findByUUID(uuid);

        return token.isPresent() && LocalDateTime.now().isBefore(token.get().getExpiresAt());
    }

    public String authenticate(String username, String password) {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        } else if (!encoder.match(user.get().getPassword(), password)) {
            return null;
        }

        return generateToken().getUuid();
    }

}
