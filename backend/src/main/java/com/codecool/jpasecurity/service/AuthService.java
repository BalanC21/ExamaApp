package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.dto.LogInDTO;
import com.codecool.jpasecurity.dto.SignUpDTO;
import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public SignUpDTO signUp(SignUpDTO signUpDTO) {
        User user = new User(signUpDTO.username(),
                signUpDTO.email(),
                encoder.encode(signUpDTO.password()),
                "ROLE_USER");
        userRepository.save(user);
        return signUpDTO;
    }

    public String authenticateUser(@Valid @RequestBody LogInDTO userLogIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogIn.username(), userLogIn.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenService.generateToken(authentication);
    }
}
