package com.locawork.webapi.service;

import com.locawork.webapi.dto.NotificationRequestDto;
import com.locawork.webapi.dto.SubscriptionRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import org.springframework.core.io.ClassPathResource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
public class NotificationService {

    @Value("${app.firebase-config}")
    private String firebaseConfig;

    private FirebaseApp firebaseApp;
    private static final Logger LOGGER=LoggerFactory.getLogger(NotificationService.class);

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
        }
    }

    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            //log.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            //log.error("Firebase unsubscribe from topic fail", e);
        }
    }

    public String sendPnsToDevice(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setToken(notificationRequestDto.getTarget())
                .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
                .putData("content", notificationRequestDto.getTitle())
                .putData("body", notificationRequestDto.getBody())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            LOGGER.error("Fail to send firebase notification", e);
        }

        return response;
    }

    public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
        Message message = Message.builder()
                .setTopic(notificationRequestDto.getTarget())
                .setNotification(new Notification(notificationRequestDto.getTitle(), notificationRequestDto.getBody()))
                .putData("content", notificationRequestDto.getTitle())
                .putData("body", notificationRequestDto.getBody())
                .build();

        String response = null;
        try {
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            LOGGER.error("Fail to send firebase notification", e);
        }

        return response;
    }
}