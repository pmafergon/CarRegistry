package com.mafer.CarRegisty.service;

import com.mafer.CarRegisty.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

    UserEntity save(UserEntity newUser);
    UserDetailsService userDetailService();
}
