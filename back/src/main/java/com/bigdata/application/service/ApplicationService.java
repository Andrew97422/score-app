package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationResponse;
import com.bigdata.application.model.dto.ScoringApplicationWithAuthRequest;
import com.bigdata.application.model.dto.ScoringApplicationWithoutAuthRequest;
import com.bigdata.application.model.entity.LoanApplicationEntity;
import com.bigdata.application.repository.ApplicationRepository;
import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.model.LendingType;
import com.bigdata.user.model.entity.UserEntity;
import com.bigdata.user.model.enums.Role;
import com.bigdata.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final UserRepository userRepository;

    private final ScoringService scoringService;

    private final MailSender mailSender;

    private final ApplicationUtils applicationUtils;


    public void addNewApplicationWithoutAuth(ScoringApplicationWithoutAuthRequest request) {
        var application = applicationUtils.mapToEntity(request);
        //SimpleMailMessage message = new SimpleMailMessage();
        //message.setText("message");
        //message.setTo("nosoff.4ndr@yandex.ru");
        //message.setSubject("Activation Code");
        //mailSender.sendEmail("andryushka.nosov.03@mail.ru", "Activation Code", "MESSAGE");
        scoringService.score(application, request.getBirthday());
    }

    @Transactional
    public void addNewApplicationWithAuth(
            ScoringApplicationWithAuthRequest request, UserEntity user
    ) {
        var application = applicationUtils.mapToEntity(request, user);
        //mailSender.sendEmail("nosoff.4ndr@yandex.ru", "Activation Code", "MESSAGE");
        scoringService.score(application, user.getBirthday());
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsList(LendingType type, UserEntity user) {
        List<ApplicationResponse> applications;
        if (user.getRole().equals(Role.USER)) {
             applications = user.getApplicationsList().stream()
                     .filter(i -> i.getLendingType().equals(type))
                     .map(applicationUtils::mapToApplicationResponse).toList();
        } else {
            applications = applicationRepository.findAll().stream()
                    .filter(i -> i.getLendingType().equals(type))
                    .map(applicationUtils::mapToApplicationResponse).toList();
        }
        log.info("Found {} loan applications", applications.size());
        log.info("Response with applications is ready");

        return applications;
    }

    @Transactional
    public void deleteApplication(Integer id, UserEntity user) {
        try {
            List<LoanApplicationEntity> newList = user.getApplicationsList();
            newList.removeIf(i->i.getId() == id);
            user.setApplicationsList(newList);
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Application {} was deleted", id);
    }

    public byte[] formPdfDocument(Integer id, UserEntity user) {
        LoanApplicationEntity application;
        if (user.getRole().equals(Role.USER)) {
            application = user.getApplicationsList()
                    .stream().filter(i->i.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
        } else {
            application = applicationRepository.getReferenceById(id);
        }

        List<CommonEntity> guides = scoringService.guides(application.getCreditAmount());
        guides = scoringService.filteredGuides(guides);

        log.info("Found {} suitable loan products for the user {}.", guides.size(), application.getId());
        try {
            return applicationUtils.formPdfDoc(application, guides);
        } catch (Exception e) {
            log.error("Problem with forming document for the application {}", application.getId());
            return new byte[0];
        }
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getApplicationById(Integer id, UserEntity user) {
        ApplicationResponse response;
        if (user.getRole().equals(Role.USER)) {
            response = applicationUtils.mapToApplicationResponse(user.getApplicationsList().stream()
                    .filter(i -> i.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new));
        } else {
            response = applicationUtils.mapToApplicationResponse(applicationRepository.getReferenceById(id));
        }
        return response;
    }
}
