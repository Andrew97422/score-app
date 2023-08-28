package com.bigdata.user.model.dto;

import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Поступившая информация о пользователе")
public class UserInfo {
    public String name;
    public UserEntity mapDtoToEntity() {
        return null;
    }
}
