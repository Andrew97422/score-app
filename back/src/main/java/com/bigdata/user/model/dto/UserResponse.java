package com.bigdata.user.model.dto;

import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Ответ пользователю на получение сущности")
public class UserResponse {

    @Schema(name = "Логин")
    private String login;

    @Schema(name = "Фамилия")
    private String lastName;

    @Schema(name = "Имя")
    private String firstName;

    @Schema(name = "Отчество")
    private String surName;

    @Schema(name = "Дата рождения")
    private LocalDate birthday;

    @Schema(name = "Телефон")
    private String phone;

    @Schema(name = "Email")
    private String email;

    public void mapEntityToDto(UserEntity user) {
        setLogin(user.getLogin());
        setEmail(user.getEmail());
        setBirthday(user.getBirthday());
        setPhone(user.getPhone());
        setLastName(user.getLastName());
        setFirstName(user.getFirstName());
        setSurName(user.getSurName());
    }
}
