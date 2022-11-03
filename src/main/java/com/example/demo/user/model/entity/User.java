package com.example.demo.user.model.entity;

import com.example.demo.device.model.UserDevice;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 40)
    private String email;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserDevice> userDevice;

    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.STRING)
    private IsActive isActive;

    @CreationTimestamp
    @JsonIgnore
    private Timestamp date;

    public void setUsername(String username)
    {
        if(!Pattern.matches("^[\\w_]{5,20}$",username))
        {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }

    public void setPassword(String password)
    {
        if(!Pattern.matches("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",password))
        {
            throw new IllegalArgumentException();
        }
        this.password = password;
    }

    public void setEmail(String email)
    {
        if(!Pattern.matches("^[\\da-zA-Z]([-_.]?[\\da-zA-Z])*@[\\da-zA-Z]([-_.]?[\\da-zA-Z])*.[a-zA-Z]{2,3}$",email))
        {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public void setRole(String role)
    {
        try
        {
            this.role = Role.valueOf(role);
        }
        catch (IllegalArgumentException e)
        {
            throw new IllegalArgumentException();
        }
    }

    public void setUserDeviceMAC(List<UserDevice> userDevice)
    {
        /*if(!Pattern.matches
                ("^[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]-[\\da-zA-Z][\\da-zA-Z]$",
                        userDeviceMAC))
        {
            throw new IllegalArgumentException();
        }*/
        this.userDevice = userDevice;
    }

    public void setIsActive(IsActive isActive)
    {
        this.isActive = isActive;
    }
}
