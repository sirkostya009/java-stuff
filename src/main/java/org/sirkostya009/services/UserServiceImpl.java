package org.sirkostya009.services;

import org.sirkostya009.models.User;

import java.util.*;

public class UserServiceImpl implements UserService {

    private final static Set<User> USERS = new HashSet<>(Set.of(
            new User("Mitchel", "mitchel", "secret_password"),
            new User("Vyacheslav", "Vyacheslav", "anime-lover"),
            new User("Jake", "gaylord666", "password"),
            new User("Bob", "XxX_KILLER_XxX", "bc32e8d53e798b2bfa4a4e569907a33f"),
            new User("Constantine", "sirkostya009", "cheersmate")
    ));

    @Override
    public boolean userExists(User user) {
        return USERS.contains(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return USERS.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    @Override
    public User authenticate(String username, String password) {
        var user = findByUsername(username);

        if (user.isEmpty() || !user.get().getPassword().equals(password))
            return null;

        return user.get();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(USERS);
    }

}
