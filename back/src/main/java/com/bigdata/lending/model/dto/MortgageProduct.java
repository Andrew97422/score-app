package com.bigdata.lending.model.dto;

import com.bigdata.lending.model.entity.MortgageEntity;
import com.bigdata.lending.model.enums.LendingType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MortgageProduct {
    @Schema(name = "name", description = "Название продукта")
    private String name;

    @Schema(name = "min_amount", description = "Минимальная сумма кредита")
    private float minLoanAmount;

    @Schema(name = "max_amount", description = "Максимальная сумма кредита")
    private float maxLoanAmount;

    @Schema(name = "min_term", description = "Минимальный срок кредита")
    private float minLoanTerm;

    @Schema(name = "max_term", description = "Максимальный срок кредита")
    private float maxLoanTerm;

    @Schema(name = "min_rate", description = "Минимальная процентная ставка")
    private float minLoanRate;

    @Schema(name = "url", description = "Ссылка на описание продукта")
    private String url;

    @Schema(name = "comment", description = "Краткое описание продукта")
    private String comment;

    @Schema(name = "addition_to_interest",
            description = "Хз что это, это на ипотеку (изменю описание, если найду листок с таблицами)",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String addToInterest;

    @Schema(name = "down_payment", description = "Первоначальный взнос за ипотеку",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String downPayment;
}
