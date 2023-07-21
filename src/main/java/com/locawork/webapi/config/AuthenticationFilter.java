package com.locawork.webapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.service.NotificationService;
import com.locawork.webapi.service.SettingsService;
import com.locawork.webapi.service.UserDataService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private NotificationService notificationService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        this.userDataService = ctx.getBean(UserDataService.class);
        this.settingsService= ctx.getBean(SettingsService.class);
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity creds = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        String token = Jwts.builder()
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("user_id", String.valueOf(userDataService.findId(((User) authentication.getPrincipal()).getUsername())));
        response.addHeader("email", ((User) authentication.getPrincipal()).getUsername());
        response.addHeader("firebase_token", userDataService.getUserFirebaseToken(userDataService.findId(((User) authentication.getPrincipal()).getUsername())));
        response.addHeader("radius", String.valueOf(settingsService
                .getUserSettings(userDataService.findId(((User) authentication.getPrincipal()).getUsername()))
                .getRadius()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        logger.debug("failed authentication while attempting to access ");

        //Add more descriptive message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Authentication Failed");
    }
}