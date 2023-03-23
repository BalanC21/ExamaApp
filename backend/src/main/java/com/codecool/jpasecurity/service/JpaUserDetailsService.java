package com.codecool.jpasecurity.service;

import com.codecool.jpasecurity.exceptions.UsernameNotFoundCustomException;
import com.codecool.jpasecurity.model.SecurityUser;
import com.codecool.jpasecurity.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundCustomException {
        return userRepository.findByUsername(username)
                .map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundCustomException(username));
    }
}
