package com.bigdata.application.model.dto;

import com.bigdata.application.model.entity.CurrentDebtLoadEntity;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.entity.TypeLoanCollateralEntity;
import com.bigdata.application.model.entity.WorkExperienceEntity;
import com.bigdata.application.model.enums.CountActiveLoans;
import com.bigdata.application.model.enums.LoanCollateralType;
import com.bigdata.application.model.enums.WorkExperience;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Заявка на скоринг", description = "Заполняется авторизованным пользователем")
public class ScoringApplicationWithAuthRequest {

    @Schema(description = "Вид кредитного продукта", name = "lendingType")
    private LendingType lendingType;

    @Schema(description = "Стаж работы", name = "workExperience")
    private WorkExperience workExperience;

    @Schema(description = "Вид обеспечения по кредиту", name = "loanCollateralType")
    private LoanCollateralType loanCollateralType;

    @Schema(description = "Количество действующих кредитов", name = "countActiveLoans")
    private CountActiveLoans countActiveLoans;

    @Schema(description = "Сумма ежемесячных платежей по кредитам (используется, если есть кредиты)", name = "currentDebtLoad")
    private float currentDebtLoad;

    @Schema(description = "Ежемесячный доход", name = "monthlyIncome")
    private float monthlyIncome;

    @Schema(description = "Сумма кредита", name = "amount")
    private float amount;

    @Schema(description = "Желаемый срок кредита (в месяцах)", name = "term")
    private float term;

    @Schema(description = "Военнослужащий или работник ОПК России", name = "military")
    private boolean military;

    @Schema(description = "Государственный служащий", name = "stateEmployee")
    private boolean stateEmployee;

    @Schema(description = "Клиент ПСБ", name = "psbClient")
    private boolean psbClient;

    @Schema(description = "Житель Данного Востока", name = "farEastInhabitant")
    private boolean farEastInhabitant;

    @Schema(description = "Житель Новых субъектов РФ", name = "newSubjectsResident")
    private boolean newSubjectsResident;

    @Schema(description = "ИТ-специалист", name = "itSpecialist")
    private boolean itSpecialist;

    public LoanApplicationEntity mapDtoToEntity(UserEntity user) {
        CurrentDebtLoadEntity currentDebtLoadEntity = CurrentDebtLoadEntity.builder()
                .amountLoanPayments(getCurrentDebtLoad())
                .monthlyIncome(getMonthlyIncome())
                .countActiveLoans(getCountActiveLoans())
                .build();

        return LoanApplicationEntity.builder()
                .workExperience(WorkExperienceEntity.builder().name(getWorkExperience()).build())
                .typeLoanCollateral(TypeLoanCollateralEntity.builder().name(getLoanCollateralType()).build())
                .currentDebtLoad(currentDebtLoadEntity)
                .creditAmount(getAmount())
                .loanTerm(getTerm())
                .isMilitary(isMilitary())
                .isStateEmployee(isStateEmployee())
                .isPsbClient(isPsbClient())
                .isFarEastInhabitant(isFarEastInhabitant())
                .isNewSubjectsResident(isNewSubjectsResident())
                .isItSpecialist(isItSpecialist())
                .user(user)
                .build();
    }
}
