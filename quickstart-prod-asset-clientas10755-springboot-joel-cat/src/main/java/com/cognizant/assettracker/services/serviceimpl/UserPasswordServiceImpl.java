package com.cognizant.assettracker.services.serviceimpl;

import com.cognizant.assettracker.models.entity.User;
import com.cognizant.assettracker.repositories.UserRepository;
import com.cognizant.assettracker.services.UserPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    @Autowired
    private UserRepository userPasswordRepository;

    @Override
    public Optional<User> findUserPasswordByEmail(String email){
        return userPasswordRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserPasswordByResetToken(String resetToken) {
        return userPasswordRepository.findByResetToken(resetToken);
    }

    @Override
    public void save(User userPassword) {
        userPasswordRepository.save(userPassword);

    }


}
