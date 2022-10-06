package com.example.demo.service;

import com.example.demo.model.ACK;
import com.example.demo.model.Seed;
import com.example.demo.repository.SeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeedService {
    private final SeedRepository seedRepository;

    @Transactional
    public ACK handleReceivedData(Seed seed)
    {
        seedRepository.save(seed);
        return ACK.builder()
                .status(200)
                .measure(true)
                .censor("")
                .mean(-1)
                .refresh(false)
                .build();
    }
}
