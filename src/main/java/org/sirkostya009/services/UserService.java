package org.sirkostya009.services;

import org.sirkostya009.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean userExists(String username);

    Optional<User> findByUsername(String username);

    User authenticate(String username, String password);

    List<User> findAll();
}
