package com.codecool.jpasecurity.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationCustomException extends AuthenticationException {
    public AuthenticationCustomException() {
        super("Not Authenticated! Ypu Need To SignUp!");
    }
}
