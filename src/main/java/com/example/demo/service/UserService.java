package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> findAllUsers() {
        return repository.selectAllUsers();
    }

    public Optional<User> findUserById(final long id) {
        return repository.selectUserById(id);
    }

    public User saveUser(final User user) {
        return repository.createUser(user);
    }

    public boolean deleteUserById(final long id) {
        return repository.deleteUserById(id);
    }
}
