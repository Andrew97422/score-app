package com.bigdata.application.model.dto;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.CountActiveLoans;
import com.bigdata.application.model.enums.LoanCollateralType;
import com.bigdata.application.model.enums.SettlementType;
import com.bigdata.application.model.enums.WorkExperience;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(name = "Заявка на скоринг", description = "Заполняется пользователем")
public class ApplicationForScoringRequest {

    @Schema(name = "Стаж работы")
    private WorkExperience workExperience;

    @Schema(name = "Вид обеспечения по кредиту")
    private LoanCollateralType loanCollateralType;

    @Schema(name = "Количество действующих кредитов")
    private CountActiveLoans countActiveLoans;

    @Schema(name = "Сумма кредита")
    private float amount;

    @Schema(name = "Срок кредита (в месяцах)")
    private float term;

    @Schema(name = "Желаемая ставка по кредиту")
    private int rate;

    @Schema(name = "Возможность внести первый взнос при необходимости")
    private boolean downPayment;

    @Schema(name = "Желаемый способ расчета с кредитором")
    private SettlementType settlement;

    @Schema(name = "Военнослужащий или работник ОПК России")
    private boolean military;

    @Schema(name = "Государственный служащий")
    private boolean stateEmployee;

    @Schema(name = "Клиент ПСБ")
    private boolean psbClient;

    @Schema(name = "Житель Данного Востока")
    private boolean farEastInhabitant;

    @Schema(name = "Житель Новых субъектов РФ")
    private boolean newSubjectsResident;

    @Schema(name = "ИТ-специалист")
    private boolean itSpecialist;

    public LoanApplicationEntity mapDtoToEntity() {
        return LoanApplicationEntity.builder()
                .creditAmount(getAmount()).loanTerm(getTerm())
                .downPayment(isDownPayment()).applicationDateTime(LocalDateTime.now())
                .isMilitary(isMilitary()).isStateEmployee(isStateEmployee())
                .isPsbClient(isPsbClient()).isFarEastInhabitant(isFarEastInhabitant())
                .isNewSubjectsResident(isNewSubjectsResident())
                .isItSpecialist(isItSpecialist())
                .build();
    }
}
