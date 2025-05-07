package com.skyNet.controller;

import com.skyNet.dto.AuthenticationDTO;
import com.skyNet.dto.LoginResponseDTO;
import com.skyNet.model.User;
import com.skyNet.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity login(){
        try {
            List<User> user = this.userRepository.findAllBy();

            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário ou senha inválidos");
        }
    }

}
