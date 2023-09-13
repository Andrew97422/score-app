package com.bigdata.auth.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Логин/пароль, которые вводит пользователь")
public class LoginRequest {
    private String login;
    private String password;
}
