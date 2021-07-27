package com.training.blinkist.repository;

import com.training.blinkist.modelentity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByusername(String username);
}
