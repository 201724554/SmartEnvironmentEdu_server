package com.example.demo.user.model.entity;

import com.example.demo.device.model.UserDevice;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.model.enumerate.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Student extends User{
    @Builder(builderMethodName = "studentBuilder")
    public Student(int id, String username, String password, String email, List<UserDevice> userDevice, Role role, IsActive isActive, Timestamp date, Educator educator)
    {
        super(id, username, password, email, role, userDevice, isActive, date);
        this.educator = educator;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    private Educator educator;

    public void setEducator(Educator educator)
    {
        this.educator = educator;
    }

    @Override
    public String toString() {
        return "Student{" +
                "username='" + super.getUsername() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", educator=" + educator +
                '}';
    }
}
