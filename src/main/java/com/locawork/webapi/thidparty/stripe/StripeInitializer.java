package com.locawork.webapi.thidparty.stripe;

import com.stripe.Stripe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StripeInitializer {

    @Value("${app.stripe.secret.key}")
    private String stripeApiKey;

    Logger logger = LoggerFactory.getLogger(StripeInitializer.class);

    @PostConstruct
    public void initialize() {
        Stripe.apiKey = stripeApiKey;
    }

}
