package com.bhavanisankar.filestorage.service;


import com.bhavanisankar.filestorage.model.User;
import com.bhavanisankar.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        if(userRepository.findByUsername(username).isPresent()){
            throw new RuntimeException("User already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}