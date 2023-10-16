package com.bigdata.products.common.service;

import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.common.model.LendingType;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final AutoLoanRepository autoLoanRepository;

    private final ConsumerRepository consumerRepository;

    private final MortgageRepository mortgageRepository;

    @Override
    public List<CommonEntity> guides(float creditAmount, LendingType lendingType) {
        List<? extends CommonEntity> guides = switch (lendingType) {
            case MORTGAGE -> mortgageRepository.findByMinLoanAmountLessThan(creditAmount);
            case CONSUMER -> consumerRepository.findByMinLoanAmountLessThan(creditAmount);
            case AUTO_LOAN -> autoLoanRepository.findByMinLoanAmountLessThan(creditAmount);
        };

        return (List<CommonEntity>) guides;
    }
}
