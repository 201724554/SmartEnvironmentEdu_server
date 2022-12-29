package com.example.demo.user.service;

import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.mail.service.MailService;
import com.example.demo.redis.entity.RegisterAuthNum;
import com.example.demo.redis.repo.RegisterAuthNumRepository;
import com.example.demo.token.repository.RefreshTokenRepository;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.repository.UserRepository;
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
    private final UserDeviceRepository userDeviceRepository;
    private final RefreshTokenRepository refreshTokenRepository;

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
        //registerAuthNumRepository.save(registerAuthNum);
        //mailService.sendAuthMail(user.getEmail(),registerAuthNum.getRegisterAuthNum());
    }

    @Transactional
    public void addStudent(String username, String studentUsername)
    {
        Student student = (Student) userRepository.findByUsernameAndIsActive(studentUsername, IsActive.YES).orElseThrow(()->{throw new IllegalArgumentException();});
        student.setEducator((Educator) userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(()->{throw new IllegalArgumentException();}));
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

    /**
     device add service
     */
    @Transactional(readOnly = true)
    public User getUser(String username)
    {
        return userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(()->{throw new IllegalArgumentException();});
    }
    @Transactional
    public void addMAC(String username, String MAC)
    {
        User user = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
        UserDevice userDevice = userDeviceRepository.findByUserDeviceMAC(MAC).orElseThrow(IllegalArgumentException::new);
        userDevice.setUser(user);
    }

    /**
     * logout
     */
    @Transactional
    public void logout(String username)
    {
        refreshTokenRepository.deleteByUsername(username);
    }

    /**
     * CustomHandshakeHandler
     */
    @Transactional
    public void test_makeAdmin(String username)
    {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user != null)
        {
            user.setRole(Role.ROLE_ADMIN.toString());
        }
    }
}
