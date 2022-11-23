package com.generation.securityapp.web.api;

import com.generation.securityapp.web.models.User;
import com.generation.securityapp.web.models.UserDTO;
import com.generation.securityapp.web.security.JwtUtil;
import com.generation.securityapp.web.services.JwtUserDetailsService;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/auth")
public class JwtAuthenticationController {

   private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserAuthDTO userAuthDTO) throws Exception {

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userAuthDTO.username, userAuthDTO.password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println(authentication.toString());

        UserDTO user = toDTO(jwtUserDetailsService.findUserByUsername(userAuthDTO.getUsername()));
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(user.getUsername());
        String jwtToken = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new AuthResponseDTO(jwtToken), HttpStatus.OK);
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
        String passwordEncriptada = encoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);
        jwtUserDetailsService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private UserDTO toDTO(User user) {
        return  UserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class UserAuthDTO {
        @NotNull
        private String username;
        @NotNull
        private String password;
    }

    @Getter
    @Setter
    @Builder
    public static class AuthResponseDTO {
        private String jwt;
    }
}



