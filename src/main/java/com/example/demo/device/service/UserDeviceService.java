package com.example.demo.device.service;

import com.example.demo.device.model.UserDevice;
import com.example.demo.device.repository.UserDeviceRepository;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDeviceService {
    private final UserDeviceRepository userDeviceRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addDevice(UserDevice userDevice)
    {
        userDeviceRepository.save(userDevice);
    }
    @Transactional
    public void registerDevice(String username, String deviceMAC)
    {
        UserDevice userDevice = userDeviceRepository.findByUserDeviceMAC(deviceMAC).orElseThrow(IllegalArgumentException::new);
        userDevice.setUser(userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(IllegalArgumentException::new));
    }
    @Transactional(readOnly = true)
    public List<UserDevice> getDeviceList(String username)
    {
        return userDeviceRepository.findAllByUser(userRepository.findByUsernameAndIsActive(username, IsActive.YES).orElseThrow(IllegalArgumentException::new));
    }
}
