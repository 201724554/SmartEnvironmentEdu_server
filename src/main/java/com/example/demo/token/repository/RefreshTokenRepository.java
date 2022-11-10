package com.example.demo.token.repository;

import com.example.demo.token.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshToken(String token);
    Optional<RefreshToken> findByUsername(String username);
    boolean existsByRefreshToken(String refreshToken);
    @Transactional
    void deleteByRefreshToken(String token);
    @Transactional
    void deleteByUsername(String username);
}
