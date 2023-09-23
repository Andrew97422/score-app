package com.bigdata.application.service;

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
public class MappingUtils {
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
}
