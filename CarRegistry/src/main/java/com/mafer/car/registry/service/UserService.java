package com.mafer.car.registry.service;

import com.mafer.car.registry.repository.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    UserEntity save(UserEntity newUser);
    UserDetailsService userDetailService();
    void addUserImage(Long id, MultipartFile imageFile) throws IOException;

    byte[] getUserImage(Long id);
}
