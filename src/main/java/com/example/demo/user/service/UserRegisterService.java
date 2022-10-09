package com.example.demo.user.service;

import com.example.demo.mail.service.MailService;
import com.example.demo.redis.RedisService;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class UserRegisterService {
    private final UserRepository userRepository;
    private final MailService mailService;
    private final RedisService redisService;
    @Transactional(readOnly = true)
    public boolean checkDuplicateUsernameAndEmail(String username, String email)
    {
        return userRepository.existsByUsername(username) || userRepository.existsByEmailAndIsActive(email,IsActive.YES);
    }
    @Transactional
    public void addUser(User user)
    {
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));
        userRepository.save(user);
        redisService.setRedisStringValue(user.getEmail(),randomAuthNum);
        mailService.sendAuthMail(user.getEmail(),randomAuthNum);
    }
    @Transactional
    public void confirmAuthentication(String username, String email, String authNum)
    {
        String storedAuthNum = redisService.getRedisStringValue(email);
        if(!authNum.equals(storedAuthNum))
        {
            throw new IllegalArgumentException();
        }

        Optional<User> optUser = userRepository.findByUsernameAndEmail(username, email);
        if(optUser.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        optUser.get().setIsActive(IsActive.YES);
        redisService.delRedisStringValue(email);
    }
    @Transactional
    public void resendAuthNum(String email)
    {
        Random random = new Random();
        String randomAuthNum = String.format("%04d", random.nextInt(10000));
        redisService.setRedisStringValue(email,randomAuthNum);
        mailService.sendAuthMail(email,randomAuthNum);
    }
}
