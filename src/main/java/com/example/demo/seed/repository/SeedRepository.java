package com.example.demo.seed.repository;

import com.example.demo.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SeedRepository extends JpaRepository<Seed, Integer> {
    List<Seed> findAllByDateBetweenAndMacIn(LocalDateTime start, LocalDateTime end, List<String> MAC);
    List<Seed> findAllByDateBetween(LocalDateTime start, LocalDateTime end);
}
