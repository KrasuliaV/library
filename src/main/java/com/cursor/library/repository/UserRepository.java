package com.cursor.library.repository;

import com.cursor.library.entity.User;
import com.cursor.library.entity.UserPermission;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.cursor.library.entity.UserPermission.*;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        final var id1 = UUID.randomUUID().toString();
        users.put(id1, new User(id1, "user1", "user1", Set.of(ROLE_WRITE)));
        final var id2 = UUID.randomUUID().toString();
        users.put(id2, new User(id2, "user2", "user2", Set.of(ROLE_WRITE, ROLE_READ)));
        final var id3 = UUID.randomUUID().toString();
        users.put(id3, new User(id3, "user3", "user3", Set.of(ROLE_READ)));
        final var id4 = UUID.randomUUID().toString();
        users.put(id4, new User(id4, "user4", "user4", Set.of(ROLE_WRITE, ROLE_READ, ROLE_ADMIN)));
        final var id5 = UUID.randomUUID().toString();
        users.put(id5, new User(id5, "user5", "user5", Set.of(ROLE_ADMIN)));
    }

    public Optional<User> findByUsername(String username) {
        return users.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public User saveUser(final String username,
                         final String password,
                         final Set<UserPermission> permissions) {
        final var user = new User(UUID.randomUUID().toString(), username, password, permissions);
        return users.put(user.getId(), user);
    }

    public User findById(String userId) {
        return users.get(userId);
    }

    public List<User> getPaginationBooks(Integer limit, Integer offset) {
        final ArrayList<User> listUser = new ArrayList<>(users.values());
        final int size = listUser.size();
        if (offset >= size) {
            return Collections.emptyList();
        } else if (limit > size || offset + limit > size) {
            return listUser.subList(offset, size);
        }
        return listUser.subList(offset, offset + limit);
    }

    public User updateBook(String userId,
                           final String username,
                           final String password,
                           final Set<UserPermission> permissions) {
        final var user = new User(userId, username, password, permissions);
        return users.put(user.getId(), user);
    }

}
