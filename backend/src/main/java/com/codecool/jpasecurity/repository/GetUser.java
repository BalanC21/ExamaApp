package com.codecool.jpasecurity.repository;

import com.codecool.jpasecurity.model.User;

import java.io.IOException;

public interface GetUser {
    User getUser() throws IOException;
}
