package com.bigdata.application.service;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringService {

    private final ApplicationRepository applicationRepository;

    private final ProductService productService;

    @Transactional
    public void score(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Application {} was registered.", application.getId());

        new Thread(() -> {
            float scoring = calculateScoring(application, birthday);
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<CommonEntity> guides = guides(application.getCreditAmount());
                guides = filteredGuides(guides);

                if (guides.isEmpty()) {
                    application.setStatus(ApplicationStatus.DENIED);
                    applicationRepository.saveAndFlush(application);
                    log.info("No suitable loan products for the user {}.", application.getId());
                } else {
                    application.setStatus(ApplicationStatus.APPROVED);
                    applicationRepository.saveAndFlush(application);
                    log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());
                }
            } else {
                application.setStatus(ApplicationStatus.DENIED);
                applicationRepository.saveAndFlush(application);
                log.info("No suitable loan products for the user {}." +
                        " Scoring is not enough.", application.getId());
            }
        }).start();
    }

    private float calculateScoring(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Calculate scoring for application {}, having {}", application.getId(), birthday);

        float total = 0;

        int age = Period.between(birthday, LocalDate.now()).getYears();
        if (21 <= age && age <= 22) total += 9;
        else if (23 <= age && age <= 45) total += 15;
        else if (46 <= age && age <= 64) total += 34;
        else if (65 <= age && age <= 70) total += 10;

        switch (application.getWorkExperience().getName()) {
            case LESS_THAN_YEAR_AND_HALF -> total += 14;
            case ONE_AND_HALF_TO_TEN -> total += 27;
            case ELEVEN_TO_TWENTY, MORE_THAN_TWENTY -> total += 34;
        }

        if (application.getCurrentDebtLoad().getAmountLoanPayments() >= 0
                && application.getCurrentDebtLoad().getMonthlyIncome() > 0) {
            float currentDebtLoad = application.getCurrentDebtLoad().getAmountLoanPayments() /
                    application.getCurrentDebtLoad().getMonthlyIncome();

            if (currentDebtLoad >= 0 && currentDebtLoad < 0.1)   total += 58;
            else if (currentDebtLoad > 0.11 && currentDebtLoad < 0.5)   total += 43;
            else if (currentDebtLoad > 0.51 && currentDebtLoad < 0.7)   total += 21;
            else if (currentDebtLoad > 0.71)   total += 10;
        }

        switch (application.getCurrentDebtLoad().getCountActiveLoans()) {
            case NO_CREDITS -> total += 40;
            case FROM_ONE_TO_TWO -> total += 34;
            case FROM_THREE_TO_FIVE -> total += 15;
            case MORE_THAN_FIVE -> total += 3;
        }

        return total;
    }

    @Transactional(readOnly = true)
    public List<CommonEntity> guides(float creditAmount) {
        return productService.guides(creditAmount);
    }

    public List<CommonEntity> filteredGuides(List<CommonEntity> guides) {
        return guides.stream()
                .sorted()
                .limit(5)
                .sorted((o1, o2) ->
                        (int) (o2.getMinLoanRate() - o1.getMinLoanRate()))
                .toList();
    }
}
