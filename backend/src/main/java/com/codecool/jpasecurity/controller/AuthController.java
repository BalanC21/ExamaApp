package com.codecool.jpasecurity.controller;

import com.codecool.jpasecurity.dto.LogInDTO;
import com.codecool.jpasecurity.dto.SignUpDTO;
import com.codecool.jpasecurity.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authService.authenticateUser(logInDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO signUpDTO) {
        return new ResponseEntity<>(authService.signUp(signUpDTO), HttpStatus.CREATED);
    }

//    @GetMapping("/logout")
//    public ResponseEntity<?> logOut(HttpServletRequest request, HttpServletResponse response) {
//        authService.logOut(request, response);
//        return ResponseEntity.ok("Logged Out!");
//    }
}
