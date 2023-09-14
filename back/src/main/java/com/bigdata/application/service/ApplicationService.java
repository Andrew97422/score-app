package com.bigdata.application.service;

import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.dto.ApplicationByTypeResponse;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.model.enums.ApplicationStatus;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.lending.model.entity.AutoLoanEntity;
import com.bigdata.lending.model.entity.ConsumerEntity;
import com.bigdata.lending.model.entity.GuideEntity;
import com.bigdata.lending.model.entity.MortgageEntity;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.lending.repository.AutoLoanRepository;
import com.bigdata.lending.repository.ConsumerRepository;
import com.bigdata.lending.repository.MortgageRepository;
import com.bigdata.user.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final AutoLoanRepository autoLoanRepository;
    private final ConsumerRepository consumerRepository;
    private final MortgageRepository mortgageRepository;
    private final EmailService emailService;

    public void addNewApplicationWithoutAuth(ScoringApplicationWithoutAuthRequest request) {
        var application = request.mapDtoToEntity();

        score(application, request.getBirthday(), request.getEmail());
    }

    public void addNewApplicationWithAuth(ScoringApplicationWithAuthRequest request, UserEntity user) throws Exception {
        var application = request.mapDtoToEntity(user);

        score(application, user.getBirthday(), user.getEmail());

        try {
            applicationRepository.save(application);
            log.info("Application {} was saved to the repository.", application.getId());
        } catch (Exception e) {
            log.error("Application {} wasn't saved. Reason: {}", application.getId(), e.getMessage());
            throw new Exception(e);
        }
    }

    private void score(LoanApplicationEntity application, LocalDate birthday,
                       String email2) {
        log.info("Application {} was registered.", application.getId());

        new Thread(() -> {
            float scoring = calculateScoring(application, birthday);
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<GuideEntity> guides = guides(application);
                guides = filteredGuides(guides);

                if (guides.isEmpty()) {
                    log.info("No suitable loan products for the user {}.", application.getId());
                } else {
                    log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());

                    String subject = "Ваш ответ по заявке, обнаружили для Вас доступные продукты.";
                    StringBuilder message = new StringBuilder("Для Вас обнаружено " +
                            guides.size() + " доступных продуктов.\n\n");
                    guides.forEach(i -> message.append(i.toMailString()));

                    sendEmail(email2, subject, message.toString());
                }
            } else {
                log.info("No suitable loan products for the user {}." +
                        " Scoring is not enough.", application.getId());
            }
        }).start();
    }

    private float calculateScoring(LoanApplicationEntity application, LocalDate birthday) {
        log.info("Calculate scoring for application {}, having {}", application.getId(), birthday);
        return application.scoreCalculate(birthday);
    }

    private List<GuideEntity> guides(LoanApplicationEntity loanApplication) {
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

    private List<GuideEntity> filteredGuides(List<GuideEntity> guides) {
        return guides.stream()
                .sorted()
                .limit(5)
                .sorted((o1, o2) ->
                        (int) (o2.getMinLoanRate() - o1.getMinLoanRate()))
                .toList();
    }

    private void sendEmail(String toAddress, String subject, String message) {
        emailService.sendSimpleEmail(toAddress, subject, message);
        log.info("Message to email {} has been sent.", toAddress);
    }

    public List<ApplicationByTypeResponse> getApplicationsList(LendingType type, UserEntity user) {
        List<LoanApplicationEntity> applicationEntities =
                user.getApplicationsList().stream()
                        .filter(i -> i.getLendingType().equals(type)).toList();
        log.info("Found {} loan applications", applicationEntities.size());

        List<ApplicationStatus> statuses = applicationEntities
                .stream().map(LoanApplicationEntity::getStatus).toList();
        log.info("Found {} statuses for each applications", statuses.size());

        List<ApplicationByTypeResponse> response = new ArrayList<>();

        for (int i = 0; i < applicationEntities.size(); i++) {
            response.add(new ApplicationByTypeResponse(applicationEntities.get(i), statuses.get(i)));
        }
        log.info("Response with applications is ready");

        return response;
    }
}
