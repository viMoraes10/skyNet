package com.skyNet.service;

import com.skyNet.model.User;
import com.skyNet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<User> getUser(String email) {
        return ResponseEntity.ok(userRepository.findUserByEmail(email));
    }
}
