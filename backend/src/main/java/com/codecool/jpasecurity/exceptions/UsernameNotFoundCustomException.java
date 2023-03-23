package com.codecool.jpasecurity.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsernameNotFoundCustomException extends UsernameNotFoundException {
    public UsernameNotFoundCustomException(String userName) {
        super("User: '" + userName + "' not found!");
    }
}
