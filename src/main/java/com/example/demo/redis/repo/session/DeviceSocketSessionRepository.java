package com.example.demo.redis.repo.session;

import com.example.demo.redis.entity.session.DeviceSocketSession;
import org.springframework.data.repository.CrudRepository;

public interface DeviceSocketSessionRepository extends CrudRepository<DeviceSocketSession, String> {
}
