package com.locawork.webapi.thidparty.stripe;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class StripeInitializer {

    @Value("${app.stripe-secret-key}")
    private String stripeSecretKey;

    Logger logger = LoggerFactory.getLogger(StripeInitializer.class);

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(stripeSecretKey).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                logger.info("Stripe for LOCWORK has been initialized");
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
