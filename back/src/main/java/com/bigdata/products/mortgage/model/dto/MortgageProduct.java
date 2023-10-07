package com.bigdata.products.mortgage.model.dto;

import com.bigdata.products.common.model.CommonProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MortgageProduct extends CommonProduct {

    @Schema(name = "addition_to_interest",
            description = "Хз что это, это на ипотеку (изменю описание, если найду листок с таблицами)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String addToInterest;

    @Schema(name = "down_payment", description = "Первоначальный взнос за ипотеку",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String downPayment;
}
