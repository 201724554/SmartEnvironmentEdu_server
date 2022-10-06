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
        if(username.length() > 20)
        {
            throw new UserRegisterException();
        }
        if(!Pattern.matches("[a-z]|\\d|[A-Z]",username))
        {
            throw new UserRegisterException();
        }
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setRole(Role role)
    {
       this.role = role;
    }

    public void setUserDeviceMAC(String userDeviceMAC)
    {
        this.userDeviceMAC = userDeviceMAC;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userDeviceMAC='" + userDeviceMAC + '\'' +
                '}';
    }
}
