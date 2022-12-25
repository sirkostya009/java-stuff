package org.sirkostya009.services;

import org.sirkostya009.models.User;

import java.util.List;

public interface UserService {
    boolean userExists(User user);

    User findByUsername(String username);

    User authenticate(String username, String password);

    List<User> findAll();
}
