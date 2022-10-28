package com.example.demo.redis.repo.token;

import com.example.demo.redis.entity.token.AccessToken;
import org.springframework.data.repository.CrudRepository;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
}
