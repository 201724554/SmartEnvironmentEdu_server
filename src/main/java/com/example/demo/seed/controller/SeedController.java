package com.example.demo.seed.controller;

import com.example.demo.DTO.ResponseDTO;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeedController {
    private final SeedService seedService;

    @GetMapping("/test/fetch")
    private ResponseDTO<List<Seed>> testFetch()
    {
        return new ResponseDTO<>(HttpStatus.OK.value(), seedService.testFetch());
    }
}
