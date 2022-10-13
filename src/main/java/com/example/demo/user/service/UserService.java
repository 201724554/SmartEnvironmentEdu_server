package com.example.demo.user.service;

import com.example.demo.mail.service.MailService;
import com.example.demo.redis.entity.RegisterAuthNum;
import com.example.demo.redis.repo.RegisterAuthNumRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final RegisterAuthNumRepository registerAuthNumRepository;
    private final MailService mailService;

    /**
     register service
     */
    @Transactional(readOnly = true)
    public boolean checkDuplicateUsernameAndEmail(String username, String email)
    {
        return userRepository.existsByUsername(username) || userRepository.existsByEmailAndIsActive(email,IsActive.YES);
    }
    @Transactional
    public void addUser(User user, RegisterAuthNum registerAuthNum)
    {
        userRepository.save(user);
        registerAuthNumRepository.save(registerAuthNum);
        mailService.sendAuthMail(user.getEmail(),registerAuthNum.getRegisterAuthNum());
    }
    @Transactional
    public void confirmAuthentication(String username, String email, String authNum)
    {
        RegisterAuthNum registerAuthNum = registerAuthNumRepository.findById(email).orElseThrow(()->{throw new IllegalArgumentException();});
        if(!registerAuthNum.getRegisterAuthNum().equals(authNum))
        {
            throw new IllegalArgumentException();
        }

        Optional<User> optUser = userRepository.findByUsernameAndEmail(username, email);
        if(optUser.isEmpty())
        {
            throw new IllegalArgumentException();
        }
        optUser.get().setIsActive(IsActive.YES);

        registerAuthNumRepository.deleteById(email);
    }
    @Transactional
    public void resendAuthNum(RegisterAuthNum registerAuthNum)
    {
        mailService.sendAuthMail(registerAuthNum.getEmail(),registerAuthNum.getRegisterAuthNum());
        registerAuthNumRepository.save(registerAuthNum);
    }
}
