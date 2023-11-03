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
@Schema(name = "Запрос на получение виджета")
public class WidgetResponse {

    @Schema(name = "id виджета")
    private Integer id;

    @Schema(name = "Процентная ставка")
    private double interestRate;

    @Schema(name = "id темы")
    private Integer themeId;
}
