package com.example.demo.seed.repository;

import com.example.demo.seed.model.Seed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeedRepository extends JpaRepository<Seed, Integer> {
}
