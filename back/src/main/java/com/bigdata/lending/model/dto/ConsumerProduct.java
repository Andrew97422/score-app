package com.bigdata.lending.model.dto;

import com.bigdata.lending.model.entity.ConsumerEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerProduct {
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

    @Schema(name = "discount", description = "Скидка (потреб)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String discount;
}
