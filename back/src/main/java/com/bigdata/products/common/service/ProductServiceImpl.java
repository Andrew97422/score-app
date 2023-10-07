package com.bigdata.products.common.service;

import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.model.CommonEntity;
import com.bigdata.products.consumer.model.entity.ConsumerEntity;
import com.bigdata.products.consumer.repository.ConsumerRepository;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final AutoLoanRepository autoLoanRepository;

    private final ConsumerRepository consumerRepository;

    private final MortgageRepository mortgageRepository;

    @Override
    public List<CommonEntity> guides(float creditAmount) {
        List<AutoLoanEntity> autoLoanEntities = autoLoanRepository
                .findByMinLoanAmountLessThan(creditAmount);
        List<MortgageEntity> mortgageEntities = mortgageRepository
                .findByMinLoanAmountLessThan(creditAmount);
        List<ConsumerEntity> consumerEntities = consumerRepository
                .findByMinLoanAmountLessThan(creditAmount);

        List<CommonEntity> guides = new ArrayList<>();
        guides.addAll(autoLoanEntities);
        guides.addAll(mortgageEntities);
        guides.addAll(consumerEntities);

        return guides;
    }
}
