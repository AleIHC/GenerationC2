package com.generation.securityapp.web.services;

import com.generation.securityapp.web.models.User;
import com.generation.securityapp.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        Boolean exists = userRepository.existsById(id);

        if (exists) {
            Optional<User> user = userRepository.findById(id);
            return user;
        }
        return null;
    }
}
