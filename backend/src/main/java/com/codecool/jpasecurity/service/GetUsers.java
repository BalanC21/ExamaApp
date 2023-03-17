package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.model.User;
import com.codecool.jpasecurity.repository.GetUser;
import com.codecool.jpasecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GetUsers implements GetUser {
    private final AuthenticationFacade authentication;
    private final UserRepository userRepository;

    public GetUsers(AuthenticationFacade authentication, UserRepository userRepository) {
        this.authentication = authentication;
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        User user = null;
        try {
            user = validateUser();
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println(exception.getMessage());
        }
        return user;
    }

    private User validateUser() throws IOException {
        boolean isAuthenticated = authentication.getAuthentication().isAuthenticated();
        if (!isAuthenticated) {
            throw new IOException("Not Authenticated");
        }
        String username = authentication.getAuthentication().getName();

        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found! " + "Username: " + username));
    }
}
