package com.example.demo.user.repository;

import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    boolean existsByEmailAndIsActive(String email, IsActive isActive);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndEmail(String username, String email);
    Optional<User> findByUsernameAndIsActive(String username, IsActive isActive);
}
