package com.bigdata.lending.service;

import com.bigdata.lending.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LendingService {
    private final GuideRepository guideRepository;


}
