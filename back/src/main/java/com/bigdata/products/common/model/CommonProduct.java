package com.bigdata.products.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class CommonProduct {

    @Schema(name = "id", description = "id продукта")
    private Integer id;

    @Schema(name = "name", description = "Название продукта")
    private String name;

    @Schema(name = "min_amount", description = "Минимальная сумма кредита")
    private float minAmount;

    @Schema(name = "max_amount", description = "Максимальная сумма кредита")
    private float maxAmount;

    @Schema(name = "min_term", description = "Минимальный срок кредита")
    private float minTerm;

    @Schema(name = "max_term", description = "Максимальный срок кредита")
    private float maxTerm;

    @Schema(name = "min_rate", description = "Минимальная процентная ставка")
    private float minRate;

    @Schema(name = "url", description = "Ссылка на описание продукта")
    private String url;

    @Schema(name = "comment", description = "Краткое описание продукта")
    private String comment;

    @Schema(name = "start_date", description = "Начало действия кредитного продукта")
    private LocalDateTime startDate;

    @Schema(name = "finish_date", description = "Конец действия кредитного продукта")
    private LocalDateTime finishDate;

    @Schema(name = "active", description = "Активный продукт")
    private boolean active;
}
