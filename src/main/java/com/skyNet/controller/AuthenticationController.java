package com.skyNet.controller;


import com.skyNet.dto.AuthenticationDTO;
import com.skyNet.dto.LoginResponseDTO;
import com.skyNet.dto.RegisterDTO;
import com.skyNet.model.User;
import com.skyNet.repository.UserRepository;
import com.skyNet.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication related requests.
 */
@RestController
@RequestMapping(value ="auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint for user login.
     *
     * @param data The authentication data transfer object containing the user's email and password.
     * @return A ResponseEntity containing the login response with the JWT token or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data ){
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());

            User user = this.userRepository.findUserByEmail(data.email());

            return ResponseEntity.ok(new LoginResponseDTO(token, user.getUsername(), data.email(),"Ok"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuário ou senha inválidos");
        }
    }

    /**
     * Endpoint for user registration.
     *
     * @param registerDTO The registration data transfer object containing the user's details.
     * @return A ResponseEntity indicating the success or failure of the registration.
     */
    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Valid RegisterDTO registerDTO ){
        try {
            if(this.userRepository.findByEmail(registerDTO.email()) != null) return ResponseEntity.badRequest().build();

            String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
            User newUser = new User(registerDTO.username(), encryptedPassword, registerDTO.role(), registerDTO.email(), true);

            this.userRepository.save(newUser);

            return ResponseEntity.ok().build();

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Request failed: " + e.getMessage());
        }
    }
}