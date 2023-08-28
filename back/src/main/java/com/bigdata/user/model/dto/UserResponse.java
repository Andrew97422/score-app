package com.bigdata.user.model.dto;

import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@Schema(name = "Ответ пользователю на получение сущности")
public class UserResponse {
    public void mapEntityToDto(UserEntity user) {
    }
}
