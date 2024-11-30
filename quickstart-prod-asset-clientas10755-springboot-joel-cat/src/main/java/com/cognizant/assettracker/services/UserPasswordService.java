package com.cognizant.assettracker.services;

import com.cognizant.assettracker.models.entity.User;

import java.util.Optional;

public interface UserPasswordService {
    public Optional<User> findUserPasswordByEmail(String email);
    public Optional<User> findUserPasswordByResetToken(String token);
    public void save(User userPassword);

}
