package com.bigdata.application.model.dto;

import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.application.model.enums.CountActiveLoans;
import com.bigdata.application.model.enums.WorkExperience;
import com.bigdata.products.common.model.LendingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private int id;

    @Schema(name = "Дата создания заявки")
    private LocalDateTime applicationDateTime;

    @Schema(name = "Согласие на обработку персональных данных")
    private boolean consentPersonalData;

    @Schema(name = "Согласие на запрос в кредитное бюро")
    private boolean consentRequestToCreditBureau;

    @Schema(name = "Согласие на рекламную рассылку")
    private boolean consentToAdvertising;

    @Schema(name = "Наличие возможных кредитных продуктов")
    private boolean opportunityToOfferLoan;

    @Schema(name = "Опыт работы")
    private WorkExperience workExperience;

    @Schema(name = "Наличие действующих кредитов")
    private boolean openLoans;

    @Schema(name = "Количество действующих кредитов")
    private CountActiveLoans countActiveLoans;

    @Schema(name = "Сумма платежей по кредитам")
    private float amountLoanPayments;

    @Schema(name = "Ежемесячный доход")
    private float monthlyIncome;

    @Schema(name = "Сумма кредита")
    private float creditAmount;

    @Schema(name = "Срок кредита")
    private float loanTerm;

    @Schema(name = "Наличие первоначального взноса")
    private boolean downPayment;

    @Schema(name = "Военный")
    private boolean isMilitary;

    @Schema(name = "Государственный служащий")
    private boolean isStateEmployee;

    @Schema(name = "Клиент ПСБ")
    private boolean isPsbClient;

    @Schema(name = "Житель дальнего Востока")
    private boolean isFarEastInhabitant;

    @Schema(name = "Житель новых регионов")
    private boolean isNewSubjectsResident;

    @Schema(name = "ИТ-специалист")
    private boolean isItSpecialist;

    @Schema(name = "Тип заявки")
    private LendingType lendingType;

    @Schema(name = "Статус заявки")
    private ApplicationStatus status;

    @Schema(name = "Id пользователя")
    private Integer user;
}
