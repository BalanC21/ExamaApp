package com.codecool.jpasecurity.repository;

import org.springframework.security.core.Authentication;

public interface IAuthentication {
    Authentication getAuthentication();
}
