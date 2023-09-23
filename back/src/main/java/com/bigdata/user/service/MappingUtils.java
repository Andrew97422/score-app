package com.bigdata.user.service;

import com.bigdata.user.model.dto.UpdateUserRequest;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
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
                .password(request.getPassword())
                .obtainedFrom(obtainedFrom)
                .build();
    }

    public void updateData(UserEntity updatedUser) {
        updatedUser.setBirthday(updatedUser.getBirthday());
        updatedUser.setLogin(updatedUser.getLogin());
        updatedUser.setEmail(updatedUser.getEmail());
        updatedUser.setPhone(updatedUser.getPhone());
        updatedUser.setFirstName(updatedUser.getFirstName());
        updatedUser.setLastName(updatedUser.getLastName());
        updatedUser.setSurName(updatedUser.getSurName());
        updatedUser.setObtainedFrom(updatedUser.isObtainedFrom());
    }
}
