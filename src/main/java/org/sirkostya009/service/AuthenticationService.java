package org.sirkostya009.service;

import org.sirkostya009.models.User;
import org.sirkostya009.repos.UserRepository;
import org.sirkostya009.security.PasswordEncoder;

public class AuthenticationService {

    private final PasswordEncoder encoder = new PasswordEncoder();
    private final UserRepository userRepository = new UserRepository();

    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public User authenticate(String username, String password) {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty() || !encoder.match(user.get().getPassword(), password))
            return null;

        return user.get();
    }

}
