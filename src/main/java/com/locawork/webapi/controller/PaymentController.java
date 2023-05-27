package com.locawork.webapi.controller;

import com.locawork.webapi.model.Pay;
import com.locawork.webapi.model.PurchaseData;
import com.locawork.webapi.service.SettingsService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    SettingsService settingsService;

    @PostMapping("/get-payment-data")
    public String getPaymentData(@RequestBody Pay pay) throws StripeException {
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey(pay.getApiVersion())

                .build();
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("customer", pay.getCustomerId());
        EphemeralKey key = EphemeralKey.create(options, requestOptions);
        return key.getRawJson();
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PurchaseData purchaseData) throws StripeException {
    // Set your secret key. Remember to switch to your live secret key in production.
    // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = purchaseData.getApiKey();

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(purchaseData.getAmount())
                        .setCurrency("eur")
                        .build();
        settingsService.purchaseMember(purchaseData.getRole(), purchaseData.getUserId());

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }


}
