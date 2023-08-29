package com.bigdata.user.model.dto;

import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Логин", name = "login")
    private String login;

    @Schema(description = "Фамилия", name = "lastName")
    private String lastName;

    @Schema(description = "Имя", name = "firstName")
    private String firstName;

    @Schema(description = "Отчество", name = "surName")
    private String surName;

    @Schema(description = "Дата рождения", name = "birthday")
    private LocalDate birthday;

    @Schema(description = "Телефон", name = "phone")
    private String phone;

    @Schema(description = "Email", name = "email")
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
