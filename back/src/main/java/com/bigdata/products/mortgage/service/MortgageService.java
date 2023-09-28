package com.bigdata.products.mortgage.service;

import com.bigdata.products.common.CommonService;
import com.bigdata.products.mortgage.model.dto.MortgageProduct;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MortgageService implements CommonService<MortgageProduct> {

    private final MortgageRepository mortgageRepository;

    private final MortgageUtils mortgageUtils;

    @Transactional
    public Integer registerNewLending(MortgageProduct mortgageProduct) {
        var mortgageEntity = mortgageUtils.mapToEntity(mortgageProduct);
        mortgageRepository.save(mortgageEntity);
        return mortgageEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        mortgageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MortgageProduct getById(Integer id) {
        return mortgageUtils.mapToDto(mortgageRepository.getReferenceById(id));
    }

    @Transactional
    public void updateById(Integer id, MortgageProduct mortgageProduct) {
        var mortgageEntity = mortgageUtils.mapToEntity(mortgageProduct);
        MortgageEntity mortgage = mortgageRepository.getReferenceById(id);
        mortgageUtils.update(mortgage, mortgageEntity);
        mortgageRepository.save(mortgage);
    }
}
