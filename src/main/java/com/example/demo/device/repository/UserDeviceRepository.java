package com.example.demo.device.repository;

import com.example.demo.device.model.UserDevice;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {
    Optional<UserDevice> findByUserDeviceMAC(String userDeviceMAC);
    List<UserDevice> findAllByUser(User user);
}
