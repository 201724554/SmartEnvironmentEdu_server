package com.example.demo.user.model.entity;

import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.IsAuthorized;
import com.example.demo.user.model.enumerate.Role;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
//@Builder(builderMethodName = "educatorBuilder")
public class Educator extends User{
    @Builder(builderMethodName = "educatorBuilder")
    public Educator(int id, String username, String password, String email, String userDeviceMAC, Role role, IsActive isActive, Timestamp date, List<Student> students, IsAuthorized isAuthorized)
    {
        super(id, username, password, email, role, userDeviceMAC, isActive, date);
        this.students = students;
        this.isAuthorized = isAuthorized;
    }
    @OneToMany(mappedBy = "educator", fetch = FetchType.LAZY)
    private List<Student> students;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    private IsAuthorized isAuthorized;
}
