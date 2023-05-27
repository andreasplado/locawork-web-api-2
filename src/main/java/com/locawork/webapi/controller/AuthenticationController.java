package com.locawork.webapi.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.locawork.webapi.model.AuthenticationRequest;
import com.locawork.webapi.model.AuthenticationResponse;
import com.locawork.webapi.service.UserDataService;
import com.locawork.webapi.service.UserAuthService;
import com.locawork.webapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    @Lazy
    private UserAuthService userAuthService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){

        boolean userExists = userDataService.userAuthenticated(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(userExists){
            String token = jwtUtil.generateToken(userAuthService.loadUserByUsername(authenticationRequest.getUsername()));
            return ResponseEntity.ok(new AuthenticationResponse(token));
        }else{
            return new ResponseEntity<>("You have no access to locawork!",
                    HttpStatus.UNAUTHORIZED);
        }


    }

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