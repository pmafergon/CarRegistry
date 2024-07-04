package com.mafer.CarRegisty.service.impl;

import com.mafer.CarRegisty.repository.UserRepository;
import com.mafer.CarRegisty.repository.entity.UserEntity;
import com.mafer.CarRegisty.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }

    @Override
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
            }
        };
    }
}
