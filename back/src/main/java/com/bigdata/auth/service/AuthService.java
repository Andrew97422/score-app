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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final AuthUtils mappingUtils;

    @Transactional
    public void register(RegisterRequest request) throws IllegalArgumentException {
        var user = mappingUtils.mapToEntity(request, false);

        if (userRepository.existsByLogin(user.getLogin())) {
            log.error("User {} was not saved. It's already exists", user.getLogin());
            throw new IllegalArgumentException("User " + user.getLogin() + " already exists");
        }
        userRepository.save(user);

        log.info("{} was registered", request.getLogin());
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword(), new ArrayList<>()
                    )
            );
        } catch (Exception e) {
            log.error("Authentication failed, reason is {}", e.getMessage());
            throw e;
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

    @Transactional
    public void registerOperator(RegisterRequest request) throws IllegalArgumentException {
        var operator = mappingUtils.mapOperatorToEntity(request);

        if (userRepository.existsByLogin(request.getLogin())) {
            log.error("User {} was not saved. It's already exists", operator.getLogin());
            throw new IllegalArgumentException("Operator " + operator.getLogin() + " already exists");
        }

        userRepository.save(operator);

        log.info("{} was registered", operator.getLogin());
    }

    @Transactional
    public void registerSuperAdmin(RegisterRequest request) throws IllegalArgumentException {
        var superAdmin = mappingUtils.mapSuperAdminToEntity(request);

        if (userRepository.existsByLogin(request.getLogin())) {
            log.error("User {} eas not saved. It's already exists", superAdmin.getLogin());
            throw new IllegalArgumentException("Superadmin " + superAdmin.getLogin() + " already exists");
        }

        userRepository.save(superAdmin);

        log.info("{} was registered", superAdmin.getLogin());
    }
}
