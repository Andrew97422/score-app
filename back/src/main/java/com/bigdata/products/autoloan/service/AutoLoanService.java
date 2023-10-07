package com.bigdata.products.autoloan.service;

import com.bigdata.products.autoloan.model.dto.AutoLoanProduct;
import com.bigdata.products.autoloan.model.entity.AutoLoanEntity;
import com.bigdata.products.autoloan.repository.AutoLoanRepository;
import com.bigdata.products.common.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AutoLoanService implements CommonService<AutoLoanProduct> {

    private final AutoLoanRepository autoLoanRepository;

    private final AutoLoanUtils autoLoanUtils;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Transactional
    public Integer registerNewLending(AutoLoanProduct autoLoanProduct) {
        var autoLoanEntity = new AutoLoanEntity();
        autoLoanUtils.mapToEntity(autoLoanProduct, autoLoanEntity);
        autoLoanRepository.save(autoLoanEntity);
        return autoLoanEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        autoLoanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AutoLoanProduct getById(Integer id) {
        var autoLoanEntity = autoLoanRepository.getReferenceById(id);
        var autoLoanProduct = new AutoLoanProduct();
        autoLoanUtils.mapToDto(autoLoanProduct, autoLoanEntity);
        return autoLoanProduct;
    }

    @Transactional
    public void updateById(Integer id, AutoLoanProduct autoLoanProduct) {
        var autoLoan = autoLoanRepository.getReferenceById(id);
        var autoLoanEntity = new AutoLoanEntity();
        autoLoanUtils.mapToEntity(autoLoanProduct, autoLoanEntity);
        autoLoanUtils.update(autoLoan, autoLoanEntity);
        autoLoanRepository.save(autoLoan);
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = autoLoanRepository.findAllSoonActive()
                .stream().map(AutoLoanEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            makeActive(soonActive);
        }
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = autoLoanRepository.findAllSoonNotActive()
                .stream().map(AutoLoanEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            makeActive(soonNotActive);
        }
    }

    @Override
    public void makeActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var autoLoanEntity = autoLoanRepository.getReferenceById(id);

            LocalDateTime timeActive = autoLoanEntity.getStartDate();
            long timeToActive = timeActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                autoLoanEntity.setActive(true);
                autoLoanRepository.save(autoLoanEntity);
            },timeToActive - LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH))
                    , TimeUnit.SECONDS);
        });
    }

    @Override
    public void makeNotActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var autoLoanEntity = autoLoanRepository.getReferenceById(id);

            LocalDateTime timeNotActive = autoLoanEntity.getFinishDate();
            long timeToNotActive = timeNotActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                        autoLoanEntity.setActive(false);
                        autoLoanRepository.save(autoLoanEntity);
                    },timeToNotActive - LocalDateTime.now()
                            .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH))
                    , TimeUnit.SECONDS);
        });
    }
}
