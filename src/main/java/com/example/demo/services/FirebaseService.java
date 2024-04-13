package com.example.demo.services;

import com.example.demo.domain.entities.Album;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface FirebaseService {
    Boolean receivedToken(String token);

    void sendMessageToFirebase(String title, String body, String token);
}

