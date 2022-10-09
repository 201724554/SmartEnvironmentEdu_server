package com.example.demo.user.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Builder(builderMethodName = "studentBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{
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
