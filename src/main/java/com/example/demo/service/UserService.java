package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service    // Annotation to tell Spring that this Class can be instantiated and injected
public class UserService {

    @Autowired  // Annotation to tell Spring that we need an instance of UserRepository to be injected
    private UserRepository repository;

    /**
     * Fetch all users
     * @return the list of users in database
     */
    public List<User> findAllUsers() {
        return repository.findAll();
    }

    /**
     * Fetch a user by its id
     * @param id id of the user
     * @return the user identified by its id, or empty if no one match
     */
    public Optional<User> findUserById(final long id) {
        return repository.findById(id);
    }

    /**
     * Insert a new user
     * @param user the user to be saved
     * @return the user saved
     */
    public User saveUser(final User user) {
        return repository.save(user);
    }

    /**
     * Delete the user identified by its id
     * @param id id of the user
     * @return true if the user could be deleted, false otherwise
     */
    public boolean deleteUserById(final long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }

        return false;
    }
}
