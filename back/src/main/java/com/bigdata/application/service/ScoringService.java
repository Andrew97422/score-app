package com.bigdata.application.service;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.CommonEntity;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScoringService {

    private final AutoLoanRepository autoLoanRepository;

    private final ConsumerRepository consumerRepository;

    private final MortgageRepository mortgageRepository;

    private final ApplicationRepository applicationRepository;

    @Transactional
    public void score(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Application {} was registered.", application.getId());

        new Thread(() -> {
            float scoring = calculateScoring(application, birthday);
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<CommonEntity> guides = guides(application);
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
        return application.scoreCalculate(birthday);
    }

    @Transactional(readOnly = true)
    public List<CommonEntity> guides(LoanApplicationEntity loanApplication) {
        List<AutoLoanEntity> autoLoanEntities = autoLoanRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<MortgageEntity> mortgageEntities = mortgageRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<ConsumerEntity> consumerEntities = consumerRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());

        List<CommonEntity> guides = new ArrayList<>();
        guides.addAll(autoLoanEntities);
        guides.addAll(mortgageEntities);
        guides.addAll(consumerEntities);

        return guides;
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
