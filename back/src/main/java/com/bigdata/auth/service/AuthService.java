package com.bigdata.auth.service;

import com.bigdata.auth.model.AuthenticationResponse;
import com.bigdata.auth.model.LoginRequest;
import com.bigdata.auth.model.RegisterRequest;
import com.bigdata.config.JwtService;
import com.bigdata.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest request) throws IllegalArgumentException {
        var user = request.mapDtoToEntity(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.existsByLogin(user.getLogin())) {
            log.error("User {} was not saved. It's already exists", user.getLogin());
            throw new IllegalArgumentException("User " + user.getLogin() + " already exists");
        }
        userRepository.save(user);

        log.info("{} was registered", request.getLogin());
    }

    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword(), new ArrayList<>()
                    )
            );
        } catch (Exception e) {
            log.error("Authentication failed, reason is {}", e.getMessage());
        }
        log.info("User {} was authenticated", request.getUsername());

        var user = userRepository.findByLogin(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User was not found!"));
        var jwtToken = jwtService.generateToken(user);

        log.info("Token for user {} generated", request.getUsername());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .build();
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    public void doLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        logoutHandler.logout(request, response, authentication);
    }
}
