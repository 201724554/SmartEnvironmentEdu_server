package com.example.demo.device.repository;

import com.example.demo.device.model.UserDevice;
import com.example.demo.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDeviceRepository extends JpaRepository<UserDevice, Integer> {
    Optional<UserDevice> findByUserDeviceMAC(String userDeviceMAC);
    boolean existsByUserDeviceMAC(String userDeviceMAC);
    List<UserDevice> findAllByUser(User user);

    List<UserDevice> findAllByUserIn(List<User> list);
}
