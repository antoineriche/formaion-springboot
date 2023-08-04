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
@Repository     // Annotation to tell Spring that this Class can be instantiated and injected
public class UserRepository {
    public final AtomicLong atomicLong = new AtomicLong(3L);
    private static final List<User> USERS = new ArrayList<>(
            List.of(
                    new User(1L, "Toto", 19),
                    new User(2L, "Titi", 22)
            )
    );

    /**
     * Select all users from the database
     * @return list of all users
     */
    public List<User> selectAllUsers() {
        return USERS;
    }

    /**
     * Select first user matching given id
     * @param id id of the searched user
     * @return the user matching the given id, empty if no one match
     */
    public Optional<User> selectUserById(final long id) {
        return USERS.stream().filter(user -> Objects.equals(user.getId(), id)).findFirst();
    }

    /**
     * Save the given user to database and setting it a unique id
     * @param user the user to be saved
     * @return the saved user with id
     */
    public User createUser(final User user) {
        final long id = atomicLong.getAndIncrement();
        user.setId(id);
        USERS.add(user);
        return user;
    }

    /**
     * Delete the user matching the given id
     * @param id id of the user to be deleted
     * @return true if the user could be deleted, false otherwise
     */
    public boolean deleteUserById(final long id) {
        if (selectUserById(id).isEmpty()) {
            return false;
        }

        USERS.removeIf(user -> user.getId() == id);
        return true;
    }
}
