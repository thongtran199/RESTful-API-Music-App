package com.example.demo.controller.firebase;

import com.example.demo.constant.Firebase;
import com.example.demo.domain.entities.Album;
import com.example.demo.services.FirebaseService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log
@RestController
public class FirebaseController {

    @Autowired FirebaseService firebaseService;

    @PostMapping(path = "/FirebaseToken/{token}")
    public Boolean postToken(@PathVariable("token") String token) {
        return firebaseService.receivedToken(token);
    }
    @GetMapping(path = "/FirebaseToken")
    public String getToken() {
        return Firebase.getToken();
    }
    @PostMapping(path = "/FirebaseToken/sendMessageTest")
    public void sendMessage() {
        firebaseService.sendMessageToFirebase("ello","heehehehehehheheh", Firebase.getToken());
    }
}
