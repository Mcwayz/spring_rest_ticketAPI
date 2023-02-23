package com.example.TicketingRestApi.security.restapi.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TicketingRestApi.security.model.User;
import com.example.TicketingRestApi.security.model.UserPrinciple;
import com.example.TicketingRestApi.security.repository.UserRepository;
import com.example.TicketingRestApi.security.restapi.jwtconfig.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();
        repository.save(user);
        var userDetails = new UserPrinciple(user);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }
    
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        var user = repository.findByEmail(request.getEmail())
            .orElseThrow();
        var userDetails = new UserPrinciple(user);
        var jwtToken = jwtService.generateToken(userDetails);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

}
