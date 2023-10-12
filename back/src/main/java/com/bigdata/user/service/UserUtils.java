package com.bigdata.user.service;

import com.bigdata.user.model.dto.UpdateUserRequest;
import com.bigdata.user.model.dto.UserResponse;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

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

    public void updateData(UserEntity user, Map<String, String> map) {
        Set<String> fields = map.keySet();
        for (String field : fields) {
            if (field.equals("login"))  user.setLogin(map.get(field));
            if (field.equals("password"))   user.setPassword(passwordEncoder.encode(map.get(field)));
            if (field.equals("lastName"))   user.setLastName(map.get(field));
            if (field.equals("firstName"))  user.setFirstName(map.get(field));
            if (field.equals("surName"))    user.setSurName(map.get(field));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            if (field.equals("birthday"))   user.setBirthday(LocalDate.parse(map.get(field), formatter));
            if (field.equals("phone"))  user.setPhone(map.get(field));
            if (field.equals("email"))  user.setEmail(map.get(field));
        }
    }

    public UserResponse mapToDto(UserEntity user) {
        return UserResponse.builder()
                .id(user.getId())
                .role(user.getRole())
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
