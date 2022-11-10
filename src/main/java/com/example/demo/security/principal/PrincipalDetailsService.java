package com.example.demo.security.principal;

import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.IsActive;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepository.findByUsernameAndIsActive(username, IsActive.YES);
        return optUser.map(PrincipalDetails::new).orElseThrow(()-> new UsernameNotFoundException("username not found"));
    }
}
