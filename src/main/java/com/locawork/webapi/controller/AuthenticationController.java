package com.locawork.webapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.locawork.webapi.config.DummyAuthenticationManager;
import com.locawork.webapi.dao.entity.SettingsEntity;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.model.AuthenticationRequest;
import com.locawork.webapi.model.AuthenticationResponse;
import com.locawork.webapi.service.CustomUserDetailsService;
import com.locawork.webapi.service.SettingsService;
import com.locawork.webapi.service.UserDataService;
import com.locawork.webapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.jsonwebtoken.impl.DefaultClaims;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private DummyAuthenticationManager authenticationManager;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private  BCryptPasswordEncoder passwordEncoder;


   /*@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request)
            throws Exception {
        try {
            String token = jwtUtil.generateToken(userAuthService.loadUserByUsername(authenticationRequest.getEmail()));


            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userAuthService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }*/


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request){
        UserEntity user = userDataService.findByEmail(authenticationRequest.getEmail());
        System.out.println("Authenticating...");
        if(user != null){
            System.out.println("User exists");
            boolean isPasswordCorrect = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());
            if(isPasswordCorrect) {
                System.out.println("User pass is correct");
                int userId = userDataService.findId(authenticationRequest.getEmail());
                JwtUtil jwtUtil = new JwtUtil();
                SettingsEntity settings = settingsService.getUserSettings(userId);
                String token = jwtUtil.generateToken(customUserDetailsService.loadUserByUsername(authenticationRequest.getEmail()));
                UsernamePasswordAuthenticationToken authReq
                        = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), passwordEncoder.encode(authenticationRequest.getPassword()));
                Authentication auth = authenticationManager.authenticate(authReq);
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(auth);
                HttpSession session = request.getSession(true);
                session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, securityContext);

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("user_id", "" + userId);
                responseHeaders.set("Authorization", token);
                responseHeaders.set("Firebase_token", "lol");
                responseHeaders.set("email", authenticationRequest.getEmail());
                responseHeaders.set("Radius", "" + settings.getRadius());
                if(user.getAddsRemoved()) {
                    responseHeaders.set("Is_without_adds", "0");
                }else{
                    responseHeaders.set("Is_without_adds", "0");
                }
                return ResponseEntity.ok().headers(responseHeaders).body(new AuthenticationResponse(token));
            }
        }else{
            return new ResponseEntity<>("User does not exist!",
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Wrong details!",
                HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for (Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }
}