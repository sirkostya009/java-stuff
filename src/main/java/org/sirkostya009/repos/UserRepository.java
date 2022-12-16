package org.sirkostya009.repos;

import org.sirkostya009.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class UserRepository {
    private final static Set<User> USERS = new HashSet<>(Set.of(
            new User("Mitchel", "mitchel", "secret_password"),
            new User("Vyacheslav", "Vyacheslav", "boy-lover"),
            new User("Jake", "gaylord666", "password"),
            new User("Bob", "XxX_KILLER_XxX", "bc32e8d53e798b2bfa4a4e569907a33f"),
            new User("Constantine", "sirkostya009", "cheersmate")
    ));

    public List<User> findAll() {
        return USERS.stream().toList();
    }

    public Optional<User> findByUsername(String username) {
        return USERS.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> save(User user) {
        if (user == null)
            return Optional.empty();
        else if (findByUsername(user.getUsername()).isPresent())
            return Optional.empty();

        USERS.add(user);

        return Optional.of(user);
    }
}
