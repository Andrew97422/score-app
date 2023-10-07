package com.bigdata.products.mortgage.service;

import com.bigdata.products.common.CommonService;
import com.bigdata.products.mortgage.model.dto.MortgageProduct;
import com.bigdata.products.mortgage.model.entity.MortgageEntity;
import com.bigdata.products.mortgage.repository.MortgageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
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
@EnableScheduling
@RequiredArgsConstructor
public class MortgageService implements CommonService<MortgageProduct> {

    private final MortgageRepository mortgageRepository;

    private final MortgageUtils mortgageUtils;

    ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(10);

    @Transactional
    public Integer registerNewLending(MortgageProduct mortgageProduct) {
        var mortgageEntity = new MortgageEntity();
        mortgageUtils.mapToEntity(mortgageProduct, mortgageEntity);
        mortgageRepository.save(mortgageEntity);
        return mortgageEntity.getId();
    }

    @Transactional
    public void deleteById(Integer id) {
        mortgageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MortgageProduct getById(Integer id) {
        var mortgageEntity = mortgageRepository.getReferenceById(id);
        var mortgageProduct = new MortgageProduct();
        mortgageUtils.mapToDto(mortgageProduct, mortgageEntity);
        return mortgageProduct;
    }

    @Transactional
    public void updateById(Integer id, MortgageProduct mortgageProduct) {
        var mortgage = mortgageRepository.getReferenceById(id);
        var mortgageEntity = new MortgageEntity();
        mortgageUtils.mapToEntity(mortgageProduct, mortgageEntity);
        mortgageUtils.update(mortgage, mortgageEntity);
        mortgageRepository.save(mortgage);
    }

    @Override
    @Scheduled(cron = "0 * * * * ?")
    public void addProductToSchedulingToSetActive() {
        List<Integer> soonActive = mortgageRepository.findAllSoonActive()
                .stream().map(MortgageEntity::getId).toList();
        if (!soonActive.isEmpty()) {
            makeActive(soonActive);
        }
    }

    @Override
    public void addProductToSchedulingToSetNotActive() {
        List<Integer> soonNotActive = mortgageRepository.findAllSoonNotActive()
                .stream().map(MortgageEntity::getId).toList();
        if (!soonNotActive.isEmpty()) {
            makeNotActive(soonNotActive);
        }
    }

    @Override
    public void makeActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var mortgageEntity = mortgageRepository.getReferenceById(id);

            LocalDateTime timeActive = mortgageEntity.getStartDate();
            long timeToActive = timeActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                mortgageEntity.setActive(true);
                mortgageRepository.save(mortgageEntity);
            }, timeToActive - LocalDateTime.now()
                    .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH)),
                    TimeUnit.SECONDS
            );
        });
    }

    @Override
    public void makeNotActive(List<Integer> ids) {
        ids.forEach((id) -> {
            var mortgageEntity = mortgageRepository.getReferenceById(id);

            LocalDateTime timeNotActive = mortgageEntity.getFinishDate();
            long timeToNotActive = timeNotActive.toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH));

            poolExecutor.schedule(() -> {
                mortgageEntity.setActive(false);
                mortgageRepository.save(mortgageEntity);
            }, timeToNotActive - LocalDateTime.now()
                    .toEpochSecond(ZoneOffset.systemDefault().getRules().getOffset(Instant.EPOCH)),
                    TimeUnit.SECONDS
            );
        });
    }
}
