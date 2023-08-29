package com.bigdata.user.model.dto;

import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "Поступившая информация о пользователе на странице регистрации")
public class UserInfo {

    @NotBlank
    @Schema(name = "Логин", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @NotBlank
    @Schema(name = "Пароль", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank
    @Schema(name = "Фамилия", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotBlank
    @Schema(name = "Имя", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @Schema(name = "Отчество", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String surName;

    @NotBlank
    @Schema(name = "Дата рождения", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate birthday;

    //@Pattern(regexp="\\+7\\s?[0-9]{3}\\s?[0-9]{2}\\s?\\d{2}-\\d{2}-\\d{4}")
    @NotBlank
    @Schema(name = "Телефон", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

    @Schema(name = "Email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Pattern(regexp = "^[a-zA-Z0-9_.±]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$")
    private String email;

    public UserEntity mapDtoToEntity(boolean obtainedFrom) {
        return UserEntity.builder()
                .lastName(getLastName())
                .firstName(getFirstName())
                .surName(getSurName())
                .birthday(getBirthday())
                .phone(getPhone())
                .email(getEmail())
                .login(getLogin())
                .password(getPassword())
                .obtainedFrom(obtainedFrom)
                .build();
    }
}
