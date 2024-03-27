package com.example.demo.authentication;


import com.example.demo.domain.entities.User;
import com.example.demo.services.UserService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CHUAN BI TIM");
        User user =  userService.findByUsername(username);
        if(user == null)
        {
            System.out.println("KHONG TIM THAY NGUOI DUNG USERNAME "+ username);
            throw new UsernameNotFoundException("Khong tim thay nguoi dung co username: "+username);
        }

        return new CustomUserDetails(user);
    }
    public UserDetails loadUserById(Long userId) throws ServletException {
        Optional<User> result =  userService.findOne(userId);
        if(result.isEmpty())
        {
            System.out.println("KHONG TIM THAY NGUOI DUNG CO ID " + userId);
            throw new ServletException("Khong tim thay nguoi dung co id: "+ userId);
        }

        return new CustomUserDetails(result.get());
    }
}