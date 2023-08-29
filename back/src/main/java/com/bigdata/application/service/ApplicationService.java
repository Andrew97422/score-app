package com.bigdata.application.service;

import com.bigdata.application.model.dto.ApplicationForScoringRequest;
import com.bigdata.application.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public void addNewApplication(ApplicationForScoringRequest request) throws Exception {
        var application = request.mapDtoToEntity();
        log.info("Application {} was registered.", application.getId());
        try {
            applicationRepository.save(application);
            log.info("Application {} was saved to the repository.", application.getId());
        } catch (Exception e) {
            log.error("Application {} wasn't saved. Reason: {}", application.getId(), e.getMessage());
            throw new Exception(e);
        }
    }
}
