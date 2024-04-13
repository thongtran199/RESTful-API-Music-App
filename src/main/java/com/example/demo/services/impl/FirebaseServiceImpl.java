package com.example.demo.services.impl;

import com.example.demo.constant.Firebase;
import com.example.demo.services.FirebaseService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.sun.xml.bind.v2.TODO;
import org.springframework.stereotype.Service;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    @Override
    public Boolean receivedToken(String token) {
        Firebase.setToken(token);
        System.out.println("DA NHAN TOKEN: " + token);
        return Boolean.TRUE;
    }

    @Override
    public void sendMessageToFirebase(String title, String body, String token) {
        Message message = Message.builder()
                .setToken(token)
                .putData("title", title)
                .putData("body", body)
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            System.out.println("Message sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

