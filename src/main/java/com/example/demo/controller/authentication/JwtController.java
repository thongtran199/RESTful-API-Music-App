package com.example.demo.controller.authentication;

import com.example.demo.authentication.CustomUserDetails;
import com.example.demo.authentication.JwtProviderToken;
import com.example.demo.domain.entities.User;
import com.example.demo.domain.response.ResponseLogin;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class JwtController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProviderToken tokenProvider;
    @Autowired
    private UserService UserService;

    @PostMapping( path = "/Login")
    public ResponseEntity<ResponseLogin> authenticateUser(@RequestBody User user) {

        try {

            User foundUser = UserService.findByUsername(user.getUsername());
            if (foundUser == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

            ResponseLogin responseLogin = ResponseLogin
                    .builder()
                    .user(foundUser)
                    .jwt(jwt)
                    .build();
            return new ResponseEntity<>(responseLogin, HttpStatus.OK);
        } catch (AuthenticationException e) {

            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/random")
    public String randomStuff(){
        return "JWT Hợp lệ mới có thể thấy được message này";
    }
}