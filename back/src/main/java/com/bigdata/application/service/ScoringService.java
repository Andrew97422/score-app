package com.bigdata.application.service;

import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.lending.model.entity.AutoLoanEntity;
import com.bigdata.lending.model.entity.ConsumerEntity;
import com.bigdata.lending.model.entity.GuideEntity;
import com.bigdata.lending.model.entity.MortgageEntity;
import com.bigdata.lending.repository.AutoLoanRepository;
import com.bigdata.lending.repository.ConsumerRepository;
import com.bigdata.lending.repository.MortgageRepository;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    private final UserRepository userRepository;

    public void score(LoanApplicationEntity application, LocalDate birthday, UserEntity user) {
        log.info("Application {} was registered.", application.getId());

        new Thread(() -> {
            float scoring = calculateScoring(application, birthday);
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<GuideEntity> guides = guides(application);
                guides = filteredGuides(guides);

                if (guides.isEmpty()) {
                    application.setStatus(ApplicationStatus.DENIED);
                    if (userRepository.findByLogin(user.getLogin()).isPresent()) {
                        List<LoanApplicationEntity> newList = user.getApplicationsList();
                        newList.add(application);
                        user.setApplicationsList(newList);
                        userRepository.save(user);
                    }
                    log.info("No suitable loan products for the user {}.", application.getId());
                } else {
                    application.setStatus(ApplicationStatus.APPROVED);
                    if (userRepository.findByLogin(user.getLogin()).isPresent()) {
                        List<LoanApplicationEntity> newList = user.getApplicationsList();
                        newList.add(application);
                        user.setApplicationsList(newList);
                        userRepository.save(user);
                    }
                    log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());
                }
            } else {
                application.setStatus(ApplicationStatus.DENIED);
                if (userRepository.findByLogin(user.getLogin()).isPresent()) {
                    List<LoanApplicationEntity> newList = user.getApplicationsList();
                    newList.add(application);
                    user.setApplicationsList(newList);
                    userRepository.save(user);
                }
                log.info("No suitable loan products for the user {}." +
                        " Scoring is not enough.", application.getId());
            }
        }).start();
    }

    private float calculateScoring(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Calculate scoring for application {}, having {}", application.getId(), birthday);
        return application.scoreCalculate(birthday);
    }

    public List<GuideEntity> guides(LoanApplicationEntity loanApplication) {
        List<AutoLoanEntity> autoLoanEntities = autoLoanRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<MortgageEntity> mortgageEntities = mortgageRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());
        List<ConsumerEntity> consumerEntities = consumerRepository
                .findByMinLoanAmountLessThan(loanApplication.getCreditAmount());

        List<GuideEntity> guides = new ArrayList<>();
        guides.addAll(autoLoanEntities);
        guides.addAll(mortgageEntities);
        guides.addAll(consumerEntities);

        return guides;
    }

    public List<GuideEntity> filteredGuides(List<GuideEntity> guides) {
        return guides.stream()
                .sorted()
                .limit(5)
                .sorted((o1, o2) ->
                        (int) (o2.getMinLoanRate() - o1.getMinLoanRate()))
                .toList();
    }
}
