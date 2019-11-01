package com.controllers;

import com.configs.security.JwtOutils;
import com.configs.security.MyBCryptPasswordEncoder;
import com.models.AuthRequest;
import com.models.AuthResponse;
import com.models.User;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class UserController {

    private com.repositories.UserRepository UserRepository;
    private MyBCryptPasswordEncoder myBCryptPasswordEncoder;
    @Autowired
    private com.repositories.UserRepository userRepository;
    @Autowired
    private JwtOutils jwtOutils;

    public UserController(UserRepository UserRepository,
                          MyBCryptPasswordEncoder myBCryptPasswordEncoder) {
        this.UserRepository = UserRepository;
        this.myBCryptPasswordEncoder = myBCryptPasswordEncoder;
    }

    @PostMapping("/sign-in")
    public AuthResponse signIn(@RequestBody AuthRequest authRequest) {
        User user = userRepository.findByUsername(authRequest.username);
        if (user != null && myBCryptPasswordEncoder.matches(authRequest.password, user.getPassword())) {
            return new AuthResponse(jwtOutils.create(user));
        }
        return null;
    }
}
