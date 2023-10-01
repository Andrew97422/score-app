package com.bigdata.user.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Ответ пользователю на получение сущности")
public class UserResponse {

    @Schema(description = "Логин", name = "login")
    private String login;

    @Schema(description = "Фамилия", name = "lastName")
    private String lastName;

    @Schema(description = "Имя", name = "firstName")
    private String firstName;

    @Schema(description = "Отчество", name = "surName")
    private String surName;

    @JsonFormat(pattern = "dd.MM.yyyy") 
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    @Schema(description = "Дата рождения", name = "birthday")
    private LocalDate birthday;

    @Schema(description = "Телефон", name = "phone")
    private String phone;

    @Schema(description = "Email", name = "email")
    private String email;
}
