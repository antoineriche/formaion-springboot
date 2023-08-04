package com.example.demo.repository;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class UserRepository {

    public final AtomicLong atomicLong = new AtomicLong(2L);
    private final static List<User> USERS = new ArrayList<>(
            List.of(
                    new User(1L, "Toto", 19),
                    new User(2L, "Titi", 22)
            )
    );

    public List<User> selectAllUsers() {
        return USERS;
    }

    public Optional<User> selectUserById(final long id) {
        return USERS.stream().filter(user -> Objects.equals(user.getId(), id)).findFirst();
    }

    public User createUser(final User user) {
        final long id = atomicLong.get();
        user.setId(id);
        USERS.add(user);
        return user;
    }

    public boolean deleteUserById(final long id) {
        if (selectUserById(id).isEmpty()) {
            return false;
        }

        USERS.removeIf(user -> user.getId() == id);
        return true;
    }
}
