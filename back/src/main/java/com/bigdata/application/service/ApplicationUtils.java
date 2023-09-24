package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.entity.CurrentDebtLoadEntity;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.entity.WorkExperienceEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.user.model.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationUtils {
    public LoanApplicationEntity mapToEntity(ScoringApplicationWithAuthRequest request, UserEntity user) {
        CurrentDebtLoadEntity currentDebtLoadEntity = CurrentDebtLoadEntity.builder()
                .amountLoanPayments(request.getCurrentDebtLoad())
                .monthlyIncome(request.getMonthlyIncome())
                .countActiveLoans(request.getCountActiveLoans())
                .build();

        WorkExperienceEntity workExperienceEntity = WorkExperienceEntity.builder()
                .name(request.getWorkExperience()).build();

        return LoanApplicationEntity.builder()
                .workExperience(workExperienceEntity)
                .currentDebtLoad(currentDebtLoadEntity)
                .lendingType(request.getLendingType())
                .creditAmount(request.getAmount())
                .status(ApplicationStatus.IN_PROGRESS)
                .applicationDateTime(LocalDateTime.now())
                .loanTerm(request.getTerm())
                .isMilitary(request.isMilitary())
                .isStateEmployee(request.isStateEmployee())
                .isPsbClient(request.isPsbClient())
                .isFarEastInhabitant(request.isFarEastInhabitant())
                .isNewSubjectsResident(request.isNewSubjectsResident())
                .isItSpecialist(request.isItSpecialist())
                .user(user)
                .build();
    }

    public LoanApplicationEntity mapToEntity(ScoringApplicationWithoutAuthRequest request) {
        CurrentDebtLoadEntity currentDebtLoadEntity = CurrentDebtLoadEntity.builder()
                .amountLoanPayments(request.getCurrentDebtLoad())
                .monthlyIncome(request.getMonthlyIncome())
                .countActiveLoans(request.getCountActiveLoans())
                .build();

        return LoanApplicationEntity.builder()
                .workExperience(WorkExperienceEntity.builder().name(request.getWorkExperience()).build())
                .currentDebtLoad(currentDebtLoadEntity)
                .creditAmount(request.getAmount())
                .status(ApplicationStatus.IN_PROGRESS)
                .applicationDateTime(LocalDateTime.now())
                .loanTerm(request.getTerm())
                .isMilitary(request.isMilitary())
                .isStateEmployee(request.isStateEmployee())
                .isPsbClient(request.isPsbClient())
                .isFarEastInhabitant(request.isFarEastInhabitant())
                .isNewSubjectsResident(request.isNewSubjectsResident())
                .isItSpecialist(request.isItSpecialist())
                .user(null)
                .build();
    }

    public ApplicationResponse mapToApplicationResponse(LoanApplicationEntity application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .consentPersonalData(application.isConsentPersonalData())
                .consentRequestToCreditBureau(application.isConsentRequestToCreditBureau())
                .consentToAdvertising(application.isConsentToAdvertising())
                .downPayment(application.isDownPayment())
                .openLoans(application.isOpenLoans())
                .opportunityToOfferLoan(application.isOpportunityToOfferLoan())
                .amountLoanPayments(application.getCurrentDebtLoad().getAmountLoanPayments())
                .applicationDateTime(application.getApplicationDateTime())
                .countActiveLoans(application.getCurrentDebtLoad().getCountActiveLoans())
                .creditAmount(application.getCreditAmount())
                .isFarEastInhabitant(application.isFarEastInhabitant())
                .isItSpecialist(application.isItSpecialist())
                .monthlyIncome(application.getCurrentDebtLoad().getMonthlyIncome())
                .isStateEmployee(application.isStateEmployee())
                .lendingType(application.getLendingType())
                .workExperience(application.getWorkExperience().getName())
                .loanTerm(application.getLoanTerm())
                .isMilitary(application.isMilitary())
                .isPsbClient(application.isPsbClient())
                .isNewSubjectsResident(application.isNewSubjectsResident())
                .status(application.getStatus())
                .user(application.getUser().getId())
                .build();
    }
}
