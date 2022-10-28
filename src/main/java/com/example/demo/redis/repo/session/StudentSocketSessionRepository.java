package com.example.demo.redis.repo.session;

import com.example.demo.redis.entity.session.StudentSocketSession;
import org.springframework.data.repository.CrudRepository;

public interface StudentSocketSessionRepository extends CrudRepository<StudentSocketSession, String> {
}
