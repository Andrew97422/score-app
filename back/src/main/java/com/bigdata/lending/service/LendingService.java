package com.bigdata.lending.service;

import com.bigdata.lending.model.dto.AutoLoanProduct;
import com.bigdata.lending.model.dto.ConsumerProduct;
import com.bigdata.lending.model.dto.MortgageProduct;
import com.bigdata.lending.model.enums.LendingType;
import com.bigdata.lending.repository.AutoLoanRepository;
import com.bigdata.lending.repository.ConsumerRepository;
import com.bigdata.lending.repository.MortgageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LendingService {
    private final AutoLoanRepository autoLoanRepository;
    private final ConsumerRepository consumerRepository;
    private final MortgageRepository mortgageRepository;

    @Transactional
    public Integer registerNewLending(Object lendingRequest, LendingType lendingType) {
        ObjectMapper objectMapper = new ObjectMapper();
        switch (lendingType) {
            case CONSUMER -> {
                ConsumerProduct consumerProduct = objectMapper.convertValue(lendingRequest, ConsumerProduct.class);
                var consumerEntity = consumerProduct.mapDtoToEntity();
                consumerRepository.save(consumerEntity);
                return consumerEntity.getId();
            }
            case MORTGAGE -> {
                MortgageProduct mortgageProduct = objectMapper.convertValue(lendingRequest, MortgageProduct.class);
                var mortgageEntity = mortgageProduct.mapDtoToEntity();
                mortgageRepository.save(mortgageEntity);
                return mortgageEntity.getId();
            }
            case AUTO_LOAN -> {
                AutoLoanProduct autoLoanProduct = objectMapper.convertValue(lendingRequest, AutoLoanProduct.class);
                var autoLoanEntity = autoLoanProduct.mapDtoToEntity();
                autoLoanRepository.save(autoLoanEntity);
                return autoLoanEntity.getId();
            }
        }
        return -1;
    }

    @Transactional
    public void deleteById(Integer id, LendingType lendingType) {
        switch (lendingType) {
            case MORTGAGE -> mortgageRepository.deleteById(id);
            case CONSUMER -> consumerRepository.deleteById(id);
            case AUTO_LOAN -> autoLoanRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public Object getById(Integer id, LendingType lendingType) {
        return switch (lendingType) {
            case CONSUMER -> consumerRepository.getReferenceById(id);
            case MORTGAGE -> mortgageRepository.getReferenceById(id);
            case AUTO_LOAN -> autoLoanRepository.getReferenceById(id);
        };
    }
}
