package com.locawork.webapi.controller;

import com.locawork.webapi.dao.entity.JobEntity;
import com.locawork.webapi.dao.entity.SettingsEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.data.Note;
import com.locawork.webapi.dto.AddingJobDTO;
import com.locawork.webapi.dto.PayingToken;
import com.locawork.webapi.model.ResponseModel;
import com.locawork.webapi.service.JobService;
import com.locawork.webapi.service.SettingsService;
import com.locawork.webapi.service.UserDataService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/docs")
public class HTMLContentController {

    @RequestMapping("/privacy-policy")
    @ResponseBody
    public ModelAndView getAll(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("privacy_policy.html");
        return modelAndView;
    }
}
