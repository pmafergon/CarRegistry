package com.mafer.car.registry.service;

import com.mafer.car.registry.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserEntity save(UserEntity newUser);
    UserDetailsService userDetailService();
}
