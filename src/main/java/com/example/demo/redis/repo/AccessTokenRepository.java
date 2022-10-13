package com.example.demo.redis.repo;

import com.example.demo.redis.entity.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
