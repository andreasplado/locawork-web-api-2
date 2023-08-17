package com.locawork.webapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locawork.webapi.dao.entity.UserEntity;
import com.locawork.webapi.model.AuthToken;
import com.locawork.webapi.service.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private String secret;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }


    public AuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/auth/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserEntity creds = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), encoder.encode(creds.getPassword()), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
        byte[] byteSecret = secret.getBytes();
        String token = Jwts.builder()
                .setSubject(((UserEntity) authentication.getPrincipal()).getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, byteSecret)
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        AuthToken authToken = new AuthToken();
        authToken.setToken(token);
        authToken.setUsername(((User) authentication.getPrincipal()).getUsername());
        String json = new ObjectMapper().writeValueAsString(authToken);
        response.getWriter().write(json);
    }
}