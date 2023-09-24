package com.bigdata.auth.service;

import com.bigdata.auth.model.RegisterRequest;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthUtils {

    private final PasswordEncoder passwordEncoder;

    public UserEntity mapToEntity(RegisterRequest request, boolean obtainedFrom) {
        return UserEntity.builder()
                .role(Role.USER)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .surName(request.getSurName())
                .birthday(request.getBirthday())
                .phone(request.getPhone())
                .email(request.getEmail())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .obtainedFrom(obtainedFrom)
                .build();
    }

    public UserEntity mapOperatorToEntity(RegisterRequest request) {
        return UserEntity.builder()
                .role(Role.OPERATOR)
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .surName(request.getSurName())
                .birthday(request.getBirthday())
                .phone(request.getPhone())
                .email(request.getEmail())
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .obtainedFrom(false)
                .build();
    }
}
