package com.bigdata.user.service;

import com.bigdata.user.model.dto.UpdateUserRequest;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtils {

    private final PasswordEncoder passwordEncoder;

    public UserEntity mapToEntity(UpdateUserRequest request, boolean obtainedFrom) {
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

    public void updateData(UserEntity user, UserEntity updatedUser) {
        user.setBirthday(updatedUser.getBirthday());
        user.setLogin(updatedUser.getLogin());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setPhone(updatedUser.getPhone());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setSurName(updatedUser.getSurName());
        user.setObtainedFrom(updatedUser.isObtainedFrom());
    }

    public UserResponse mapToDto(UserEntity user) {
        return UserResponse.builder()
                .login(user.getLogin())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .phone(user.getPhone())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .surName(user.getSurName())
                .build();
    }
}
