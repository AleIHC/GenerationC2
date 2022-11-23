package com.generation.securityapp.web.api;

import com.generation.securityapp.web.models.User;
import com.generation.securityapp.web.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserApiRestController {

    private final UserServiceImpl userServiceImpl;


    @RequestMapping("/obtener/usuario")
    public Optional<User> obtenerUsuario(@RequestParam(value="id",required = true) Long id) {
        return userServiceImpl.getUserById(id);
    }

}
