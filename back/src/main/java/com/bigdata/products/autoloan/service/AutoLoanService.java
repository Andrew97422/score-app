package com.bigdata.products.autoloan.service;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AutoLoanService implements CommonService<AutoLoanProduct> {

    private final AutoLoanRepository autoLoanRepository;

    private final AutoLoanUtils autoLoanUtils;

    @Transactional
    public Integer registerNewLending(AutoLoanProduct autoLoanProduct) {
        var autoLoanEntity = autoLoanUtils.mapToEntity(autoLoanProduct);
        autoLoanRepository.save(autoLoanEntity);
        return autoLoanEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        autoLoanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AutoLoanProduct getById(Integer id) {
        return autoLoanUtils.mapToDto(autoLoanRepository.getReferenceById(id));
    }

    @Transactional
    public void updateById(Integer id, AutoLoanProduct autoLoanProduct) {
        var autoLoanEntity = autoLoanUtils.mapToEntity(autoLoanProduct);
        AutoLoanEntity autoLoan = autoLoanRepository.getReferenceById(id);
        autoLoanUtils.update(autoLoan, autoLoanEntity);
        autoLoanRepository.save(autoLoan);
    }
}
