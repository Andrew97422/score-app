package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationForScoringRequest;
import com.bigdata.application.model.dto.ApplicationResponseByType;
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
    private final List<Thread> threads = new ArrayList<>();

    public void addNewApplication(ApplicationForScoringRequest request, UserEntity user, boolean isAuth) throws Exception {
        var application = request.mapDtoToEntity(user);

        log.info("Application {} was registered.", application.getId());

        Thread thread = new Thread(() -> {
            float scoring = scoringCalculation(application, request.getBirthday());
            log.info("The scoring is calculated for the user {}.", application.getId());

            application.setFinalScoring(scoring);

            if (scoring > 106) {
                List<GuideEntity> guides = guides(application);
                guides = filteredGuides(guides, application);

                if (guides.isEmpty()) {
                    log.info("No suitable loan products for the user {}.", application.getId());
                } else {
                    log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());

                    String email = request.getEmail();
                    String subject = "Ваш ответ по заявке, обнаружили для Вас доступные продукты.";
                    StringBuilder message = new StringBuilder("Для Вас обнаружено " +
                            guides.size() + " доступных продуктов.\n\n");
                    guides.forEach(i -> message.append(i.toMailString()));

                    sendEmail(email, subject, message.toString());
                }
            } else {
                log.info("No suitable loan products for the user {}." +
                        " Scoring is not enough.", application.getId());
            }
        });

        threads.add(thread);

        if (isAuth) {
            try {
                applicationRepository.save(application);
                log.info("Application {} was saved to the repository.", application.getId());
            } catch (Exception e) {
                log.error("Application {} wasn't saved. Reason: {}", application.getId(), e.getMessage());
                throw new Exception(e);
            }

        }

        threads.forEach(Thread::start);
    }

    private float scoringCalculation(LoanApplicationEntity application, LocalDate birthday) {
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

    private List<GuideEntity> filteredGuides(List<GuideEntity> guides, LoanApplicationEntity application) {
        return guides.stream()
                .filter(i -> i.getMinLoanRate() > application.getMaxBid())
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

    public List<ApplicationResponseByType> getApplicationsList(LendingType type, UserEntity user) {
        List<LoanApplicationEntity> applicationEntities =
                user.getApplicationsList().stream()
                        .filter(i -> i.getLendingType().equals(type)).toList();

        List<ApplicationStatus> statuses = applicationEntities
                .stream().map(LoanApplicationEntity::getStatus).toList();

        List<ApplicationResponseByType> response = new ArrayList<>();

        for (int i = 0; i < applicationEntities.size(); i++) {
            response.add(new ApplicationResponseByType(applicationEntities.get(i), statuses.get(i)));
        }

        return response;
    }
}
