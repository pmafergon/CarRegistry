package com.mafer.car.registry.controller;

import com.mafer.car.registry.controller.dto.LoginRequest;
import com.mafer.car.registry.controller.dto.SignUpRequest;
import com.mafer.car.registry.service.UserService;
import com.mafer.car.registry.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest request){
        try {
            return ResponseEntity.ok(authenticationService.signup(request));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/userImage/{id}/add")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> addImage(@PathVariable Long id, @RequestParam("imageFile")MultipartFile imageFile){
        try{
            if(!imageFile.getOriginalFilename().contains(".png") || !imageFile.getOriginalFilename().contains(".jpg")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            userService.addUserImage(id,imageFile);
            return ResponseEntity.ok("Image successfully saved.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @GetMapping(value = "/userImage/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long id){
        try {

            byte[] imageBytes = userService.getUserImage(id);
            HttpHeaders headers=new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            return new ResponseEntity<>(imageBytes,headers,HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
