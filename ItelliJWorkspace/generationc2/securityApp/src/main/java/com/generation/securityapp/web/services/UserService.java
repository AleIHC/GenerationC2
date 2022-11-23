package com.generation.securityapp.web.services;


import com.generation.securityapp.web.models.User;

import java.util.Optional;


public interface UserService  {

    public Optional<User> getUserById(Long id);

}

