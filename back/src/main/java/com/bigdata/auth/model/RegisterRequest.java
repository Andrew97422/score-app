package com.bigdata.auth.model;

import com.bigdata.user.model.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@Schema(name = "Поступившая информация о пользователе на странице регистрации")
public class RegisterRequest {

    @NotBlank
    @Schema(description = "Логин", requiredMode = Schema.RequiredMode.REQUIRED, name = "login")
    private String login;

    @NotBlank
    @Schema(description = "Пароль", requiredMode = Schema.RequiredMode.REQUIRED, name = "password")
    private String password;

    @NotBlank
    @Schema(description = "Фамилия", requiredMode = Schema.RequiredMode.REQUIRED, name = "lastName")
    private String lastName;

    @NotBlank
    @Schema(description = "Имя", requiredMode = Schema.RequiredMode.REQUIRED, name = "firstName")
    private String firstName;

    @Schema(description = "Отчество", requiredMode = Schema.RequiredMode.NOT_REQUIRED, name = "surName")
    private String surName;

    @NotBlank
    @JsonFormat(pattern = "dd.MM.yyyy") 
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "Дата рождения", requiredMode = Schema.RequiredMode.REQUIRED, name = "birthday")
    private LocalDate birthday;

    @NotBlank
    @Schema(description = "Телефон", requiredMode = Schema.RequiredMode.REQUIRED, name = "phone")
    private String phone;

    @Schema(description = "Email", requiredMode = Schema.RequiredMode.NOT_REQUIRED, name = "email")
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
