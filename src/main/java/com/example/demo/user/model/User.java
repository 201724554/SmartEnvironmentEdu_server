package com.example.demo.user.model;

import com.example.demo.user.exception.UserRegisterException;
import lombok.*;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false ,length = 20)
    private String userDeviceMAC;

    public void setUsername(String username) throws UserRegisterException
    {
        if(!Pattern.matches("^[\\w_]{5,20}$",username))
        {
            throw new UserRegisterException();
        }
        this.username = username;
    }

    public void setPassword(String password) throws UserRegisterException
    {
        if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",password))
        {
            throw new UserRegisterException();
        }
        this.password = password;
    }

    public void setEmail(String email) throws UserRegisterException
    {
        if(!Pattern.matches("^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$",email))
        {
            throw new UserRegisterException();
        }
        this.email = email;
    }

    public void setRole(Role role)
    {
       this.role = role;
    }

    public void setUserDeviceMAC(String userDeviceMAC) throws UserRegisterException
    {
        if(!Pattern.matches
                ("^[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]$",
                        userDeviceMAC))
        {
            throw new UserRegisterException();
        }
        this.userDeviceMAC = userDeviceMAC;
    }
}
