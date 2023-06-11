package com.locawork.webapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.locawork.webapi.WebApiApplication;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.model.AuthenticationRequest;
import com.locawork.webapi.model.AuthenticationResponse;
import com.locawork.webapi.service.UserDataService;
import com.locawork.webapi.service.UserAuthService;
import com.locawork.webapi.util.JwtUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.jsonwebtoken.impl.DefaultClaims;

@RequestMapping("/auth-controller")
@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
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
    }


    /*@RequestMapping(value = "/authenticater", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletRequest request){

        boolean userExists = userDataService.userAuthenticated(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if(userExists){
            String token = jwtUtil.generateToken(userAuthService.loadUserByUsername(authenticationRequest.getEmail()));


            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            Authentication auth = authenticationManager.authenticate(authReq);

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);


            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);


            return ResponseEntity.ok(new AuthenticationResponse(token));
        }else{
            return new ResponseEntity<>("You have no access to locawork!",
                    HttpStatus.UNAUTHORIZED);
        }


    }*/

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
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