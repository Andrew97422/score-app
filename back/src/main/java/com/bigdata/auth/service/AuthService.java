package com.bigdata.auth.service;

import com.bigdata.auth.model.AuthenticationResponse;
import com.bigdata.auth.model.LoginRequest;
import com.bigdata.auth.model.RegisterRequest;
import com.bigdata.config.JwtService;
import com.bigdata.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) {
        var user = request.mapDtoToEntity(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("{} was registered", request.getLogin());
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );
        var user = userRepository.findByLogin(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User was not found!"));
        var jwtToken = jwtService.generateToken(user);

        log.info("Token for user {} generated", request.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }
}
