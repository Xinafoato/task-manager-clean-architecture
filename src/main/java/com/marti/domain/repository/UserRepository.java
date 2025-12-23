package com.marti.domain.repository;

import com.marti.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(String userId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteById(String userId);
}
