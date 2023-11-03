package com.bigdata.widget.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Запрос на создание или обновление темы")
public class ThemeRequest {

    @Schema(name = "Цвет")
    private String color;

    @Schema(name = "Шрифт")
    private String font;

    @Schema(name = "Название")
    private String name;
}
