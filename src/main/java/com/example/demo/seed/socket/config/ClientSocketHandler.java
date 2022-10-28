package com.example.demo.seed.socket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.demo.redis.entity.session.EducatorSocketSession;
import com.example.demo.redis.entity.session.StudentSocketSession;
import com.example.demo.redis.repo.session.EducatorSocketSessionRepository;
import com.example.demo.redis.repo.session.StudentSocketSessionRepository;
import com.example.demo.security.jwt.Properties;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Component
@RequiredArgsConstructor
public class ClientSocketHandler extends TextWebSocketHandler {
    private final UserRepository userRepository;
    private final StudentSocketSessionRepository studentSocketSessionRepository;
    private final EducatorSocketSessionRepository educatorSocketSessionRepository;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try
        {
            String payload = message.getPayload();
            int tokenStart = payload.indexOf(Properties.PREFIX);
            if(tokenStart != -1)
            {
                String token = payload.substring(tokenStart,tokenStart+205).replace(Properties.PREFIX,"");
                try
                {
                    String username = JWT.require(Algorithm.HMAC512(Properties.KEY)).build().verify(token).getClaim(Properties.CLAIM_USERNAME).asString();
                    User user = userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(IllegalArgumentException::new);

                    if(user.getRole() == Role.ROLE_STUDENT)
                    {
                        StudentSocketSession studentSocketSession = StudentSocketSession.builder()
                                .studentId(user.getUsername())
                                .studentSession(session)
                                .build();
                        studentSocketSessionRepository.save(studentSocketSession);

                        String educatorName = ((Student) user).getEducator().getUsername();
                        educatorSocketSessionRepository.findById(educatorName)
                                .ifPresent(educatorSocketSession -> educatorSocketSession.getStudentSession().put(user.getUsername(), studentSocketSession.getStudentSession()));

                    }
                    else
                    {
                        EducatorSocketSession educatorSocketSession = EducatorSocketSession.builder()
                                .educatorId(user.getUsername())
                                .educatorSession(session)
                                .studentSession(new HashMap<>())
                                .build();
                        educatorSocketSessionRepository.save(educatorSocketSession);
                    }
                }
                catch (TokenExpiredException e)
                {
                    System.out.println("token");
                    session.close();
                }
                catch (IllegalArgumentException e)
                {
                    System.out.println("ill");
                    session.close();
                }
            }

            int jsonStart = payload.lastIndexOf('{');
            int jsonEnd = payload.lastIndexOf('}');

            if (jsonStart != -1 && jsonEnd != -1)
            {
                if (jsonStart > jsonEnd)
                {
                    throw new IllegalArgumentException();
                }
                String jsonString = payload.substring(jsonStart, jsonEnd + 1);

                JSONParser jsonParser = new JSONParser(jsonString);
                LinkedHashMap<String, Object> json = jsonParser.object();
                json.forEach((idx, elem) -> System.out.println(elem));
            }
        }
        catch (Exception e)
        {
            System.out.println("ee");
            session.close();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
