package com.generation.securityapp.web.repositories;

import com.generation.securityapp.web.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
}
