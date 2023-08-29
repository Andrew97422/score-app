package com.bigdata.application.model.dto;

import com.bigdata.application.model.entity.CurrentDebtLoadEntity;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.entity.TypeLoanCollateralEntity;
import com.bigdata.application.model.entity.WorkExperienceEntity;
import com.bigdata.application.model.enums.CountActiveLoans;
import com.bigdata.application.model.enums.LoanCollateralType;
import com.bigdata.application.model.enums.WorkExperience;
import com.bigdata.user.model.entity.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(name = "Заявка на скоринг", description = "Заполняется пользователем")
public class ApplicationForScoringRequest {

    @Schema(name = "Email для отправки результата")
    private String email;

    @Schema(name = "Стаж работы")
    private WorkExperience workExperience;

    @Schema(name = "Вид обеспечения по кредиту")
    private LoanCollateralType loanCollateralType;

    @Schema(name = "Количество действующих кредитов")
    private CountActiveLoans countActiveLoans;

    @Schema(name = "Сумма ежемесячных платежей по кредитам")
    private float currentDebtLoad;

    @Schema(name = "Ежемесячный доход")
    private float monthlyIncome;

    @Schema(name = "Сумма кредита")
    private float amount;

    @Schema(name = "Желаемый срок кредита (в месяцах)")
    private float term;

    @Schema(name = "Минимальная желаемая ставка по кредиту")
    private float minRate;

    @Schema(name = "Максимальная желаемая ставка по кредиту")
    private float maxRate;

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

    @Schema(name = "День рождения")
    private LocalDate birthday;

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
                .loanTerm(getTerm()).minBid(getMinRate()).maxBid(getMaxRate())
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
