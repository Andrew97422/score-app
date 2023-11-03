package com.bigdata.widget.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "Ответ на получение темы")
public class ThemeResponse {

    @Schema(name = "id темы")
    private Integer id;

    @Schema(name = "Цвет")
    private String color;

    @Schema(name = "Шрифт")
    private String font;

    @Schema(name = "Название")
    private String name;
}
