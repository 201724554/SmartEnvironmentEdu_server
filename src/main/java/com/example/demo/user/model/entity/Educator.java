package com.example.demo.user.model.entity;

import com.example.demo.user.model.enumerate.IsAuthorized;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "educatorBuilder")
public class Educator extends User{
    @OneToMany(mappedBy = "educator", fetch = FetchType.LAZY)
    private List<Student> students;

    @Column(length = 3)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private IsAuthorized isAuthorized = IsAuthorized.NO;
}
