package com.example.demo.seed.model;

import com.example.demo.seed.misc.Misc;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 20)
    private String MAC;

    @Nullable
    @Builder.Default
    private float HUM = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float TEMP = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float TUR = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float PH = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float DUST = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float DO = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float CO2 = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float LUX = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float HUM_EARTH = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float PRE = Misc.SeedDefaultValue;

    @CreationTimestamp
    Timestamp createTime;
}
