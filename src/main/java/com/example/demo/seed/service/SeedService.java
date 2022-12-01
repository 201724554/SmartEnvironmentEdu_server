package com.example.demo.seed.service;

import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.seed.model.ACK;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.repository.SeedRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeedService {
    private final SeedRepository seedRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Seed> getDataByDateAndUsername(LocalDateTime start, LocalDateTime end, String username)
    {

        if(username.equals(""))
        {
            return seedRepository.findAllByDateBetween(start, end);
        }
        else
        {

            List<String> MacList = new ArrayList<>();
            userRepository.findByUsername(username).orElseThrow(()-> {throw new IllegalArgumentException();}).getUserDevice().forEach(elem -> {
                MacList.add(elem.getUserDeviceMAC());
            });
            return seedRepository.findAllByDateBetweenAndMacIn(start, end, MacList);
        }
    }

    @Transactional
    public void saveData(List<Seed> list)
    {
        System.out.println(list);
        seedRepository.saveAll(list);
    }

    @Transactional
    public ACK handleReceivedData(Seed seed)
    {
        //seedRepository.save(seed);
        return ACK.builder()
                .status(200)
                .measure(true)
                .censor("")
                .mean(-1)
                .refresh(false)
                .build();
    }
}
